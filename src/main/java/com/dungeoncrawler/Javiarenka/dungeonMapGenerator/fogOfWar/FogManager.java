package com.dungeoncrawler.Javiarenka.dungeonMapGenerator.fogOfWar;

import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

//TODO: tworzenie fog of war w htmlu poprzez nowe Divy mocno spowalnia html. Spróbować zarządzać opacity istniejących divów poprzez dodawanie/usuwanie im dodatkowej klasy.
// pod grid trzeba dodać duży div z czarnym backgroundem

public class FogManager
{
    private transient Stage stage;
    private transient TileNavigator tileNav;
    private transient PartyAvatar party;
    public Set<Tile> raytracedTiles = new HashSet<>();
    private Set<Tile> circleLitTiles = new HashSet<>();
    private List<Tile> allTilesThatChangedVisibility = new ArrayList<>();
    private List<Tile> tilesThatChangedVisibilityNonAnimated = new ArrayList<>();
    private Map<Double, Tile> newlyShownTilesToAnimate = new LinkedHashMap<>();
    private Map<Double, Tile> newlyHiddenTilesToAnimate = new LinkedHashMap<>();

    //TODO: później poustawiać na private i dodać gettery
    public List<Tile> previouslyVisibleTiles = new ArrayList<>();
    public List<Tile> currentlyVisibleTiles = new ArrayList<>();
    public Map<Tile, Double> currentlyVisibleTilesByDistance = new LinkedHashMap<>();

    private List<Tile> tilesToShowInstant = new ArrayList<>();
    private List<Tile> tilesToHideInstant = new ArrayList<>();
    private Map<Double, Tile> tilesToShowAnimated = new LinkedHashMap<>();
    private Map<Double, Tile> tilesToHideAnimated = new LinkedHashMap<>();



    public FogManager(Stage stage)
    {
        this.stage = stage;
        this.tileNav = new TileNavigator(stage);
    }

    public PartyAvatar getParty()
    {
        return party;
    }

    public void setParty(PartyAvatar party)
    {
        this.party = party;
        updateVisibility();
    }

    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

    private double getDistanceToParty(Tile tile)
    {
        return tileNav.getDistanceBetweenTiles(party.getOccupiedTile(), tile);
    }

    public void updateVisibility()
    {
        previouslyVisibleTiles.clear();
        previouslyVisibleTiles.addAll(currentlyVisibleTiles);
        previouslyVisibleTiles.forEach(Tile::makeNotVisible);

        currentlyVisibleTiles.clear();
        currentlyVisibleTilesByDistance.clear();
        evaluatePartyVisibilityCircle();
        currentlyVisibleTiles.addAll(circleLitTiles);

        Set<Tile> newlyShownTiles = new HashSet<>(currentlyVisibleTiles);
        sortByDistanceToParty(newlyShownTiles).forEach(tile -> currentlyVisibleTilesByDistance.put(tile, getDistanceToParty(tile)));
        System.out.println("currently vis: " + currentlyVisibleTiles.size());
        System.out.println("currently vis with distance: " + currentlyVisibleTilesByDistance.size());
    }

    public void updateVisibility2()
    {
        //TODO: filtrować i odrzucać pola, których visibility się wcale nie zmieniło.

        List<Tile> previouslyInfluencedTiles = new ArrayList<>(allTilesThatChangedVisibility);
        List<Tile> previouslyVisibleTiles = allTilesThatChangedVisibility.stream().filter(tile -> tile.getVisibility() > 0).collect(Collectors.toList());
//        List<Tile> previouslyVisibleTiles = new ArrayList<>(tilesThatChangedVisibility);
        allTilesThatChangedVisibility.clear();
        tilesThatChangedVisibilityNonAnimated.clear();

//        System.out.println("prev Infl: " + previouslyInfluencedTiles.size());

        evaluatePartyVisibilityCircle();
        allTilesThatChangedVisibility.addAll(circleLitTiles);
        tilesThatChangedVisibilityNonAnimated.addAll(circleLitTiles);
//        System.out.println("circle: " + circleLitTiles.size());

        sendRays();
        System.out.println("raytraced tiles: " + raytracedTiles.size());
        allTilesThatChangedVisibility.addAll(raytracedTiles);
//        System.out.println("raytrace: " + raytracedTiles.size() );
        allTilesThatChangedVisibility = allTilesThatChangedVisibility.stream().distinct().collect(Collectors.toList());

        Set<Tile> newlyShownTiles = raytracedTiles.stream().filter(tile -> previouslyVisibleTiles.indexOf(tile) == -1).collect(Collectors.toSet());
//        System.out.println("newly shown: " + newlyShownTiles.size() );

        Set<Tile> newlyHiddenTiles = previouslyVisibleTiles.stream().filter(tile -> allTilesThatChangedVisibility.indexOf(tile) == -1).collect(Collectors.toSet()); //wyfiltruj te Tile, których nie ma w aktualnie widocznych
//        newlyHiddenTiles = newlyHiddenTiles.stream().filter(tile -> tile.getVisibility() <= 0).collect(Collectors.toSet());

        newlyHiddenTiles.forEach(tile -> tile.setVisibility(0));

        //Set<Tile> newlyHiddenTiles = new HashSet<>(newlyHiddenTiles);
//        System.out.println("newly hidden: " + newlyHiddenTiles.size() );

        allTilesThatChangedVisibility.addAll(newlyHiddenTiles);
//        System.out.println("newly hidden amount: " + newlyHiddenTiles.size());

        newlyShownTilesToAnimate.clear();
        newlyHiddenTilesToAnimate.clear();

        sortByDistanceToParty(newlyShownTiles).forEach(tile -> newlyShownTilesToAnimate.put(getDistanceToParty(tile), tile));
        sortByDistanceToParty(newlyHiddenTiles).forEach(tile -> newlyHiddenTilesToAnimate.put(getDistanceToParty(tile), tile));

//        sortByDistanceToParty(newlyHiddenTiles).forEach(tile -> {
//            System.out.println("Newly hidden tile: " + tile.getX() + "/" + tile.getY());
//        });

        //////newlyHiddenTilesWithDistance.forEach((tile, distance) -> System.out.println("Newly hidden tile: " + tile.getX() + "/" + tile.getY() + ". Distance: " + distance));
    }

    public List<Tile> sortByDistanceToParty(Set<Tile> entrySet)
    {
        List<Tile> outputList = new ArrayList<>(entrySet);

        outputList.sort((t1, t2) -> {
            if (getDistanceToParty(t1) < getDistanceToParty(t2)) return -1;
            if (getDistanceToParty(t1) > getDistanceToParty(t2)) return 1;
            return 0;
        });

        return outputList;
    }

    public void evaluatePartyVisibilityCircle()
    {
        circleLitTiles.clear();
        List<Tile> reachedTiles = tileNav.getSurroundingTilesFullSquare(party.getOccupiedTile(), FogSettings.BORDER_VISIBILITY_RADIUS);

        double x1 = FogSettings.FULL_VISIBILITY_RADIUS;
        double y1 = 1;

        double x2 = FogSettings.BORDER_VISIBILITY_RADIUS;
        double y2 = 0;

        double a;
        double b;

        a = (y2 - y1) / (x2 - x1);
        b = y1 - (a * x1);

        reachedTiles.forEach(tile -> {
            double distance = getDistanceToParty(tile);
            if (distance <= FogSettings.FULL_VISIBILITY_RADIUS + 0.35)
            {
                tile.setVisibility(1);
            }
            else if (distance > FogSettings.BORDER_VISIBILITY_RADIUS)
            {
                tile.setVisibility(0);
            }
            else
            {
                tile.setVisibility(a * distance + b);
            }
        });

        circleLitTiles.addAll(reachedTiles.stream().filter(tile -> tile.getVisibility() > 0).collect(Collectors.toList()));
    }

    public List<Tile> evaluatePartyVisibilityCone()
    {
        if (Objects.isNull(party.getDirection()))
        {
            List<Tile> outputList = new ArrayList<>();
            outputList.add(party.getOccupiedTile());
            return outputList;
        }

        List<Tile> reachedTiles = tileNav.getConeOfTiles(party.getOccupiedTile(), party.getDirection(), 8, 2, 1);
        reachedTiles.forEach(tile -> tile.setVisibility(1));

        return reachedTiles.stream().filter(tile -> tile.getVisibility() > 0).collect(Collectors.toList());
    }

    public List<Tile> getAllTilesThatChangedVisibility()
    {
        return allTilesThatChangedVisibility;
    }

    public Map<Double, Tile> getNewlyShownTilesToAnimate()
    {
        return newlyShownTilesToAnimate;
    }

    public Map<Double, Tile> getNewlyHiddenTilesToAnimate()
    {
        return newlyHiddenTilesToAnimate;
    }

    public void saveThisFogManager()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try
        {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/" + "fogManager.txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendRays()
    {
        if (Objects.isNull(party.getDirection())) return;

        double intensity = 100 + FogSettings.INITIAL_ADDITIONAL_RAYTRACE_INTENSITY;

        raytracedTiles.clear();

        party.getOccupiedTile().setVisibility(1);
        raytracedTiles.add(party.getOccupiedTile());

        sendRays(party.getOccupiedTile(), party.getDirection(), intensity, 1);
    }

    public void sendRays(Tile radiatingTile, Direction dir, double intensity, double fadeMultiplier)
    {
        sendRay(radiatingTile, dir, intensity, fadeMultiplier);
        sendRay(radiatingTile, Direction.getLeftPerpendicularDir(dir), intensity, fadeMultiplier * FogSettings.RAYTRACE_BENT_FADE_MULTIPLIER);
        sendRay(radiatingTile, Direction.getRightPerpendicularDir(dir), intensity, fadeMultiplier * FogSettings.RAYTRACE_BENT_FADE_MULTIPLIER);
    }

    public void sendRay(Tile sourceTile, Direction dir, double intensity, double fadeMultiplier)
    {
        double newIntensity = intensity - FogSettings.RAYTRACE_FADE * fadeMultiplier;

        if (newIntensity <= 0) return;
        Tile litTile = tileNav.getNextTile(sourceTile, dir);

        if (litTile.getType() != TileType.CORRIDOR && !litTile.getType().isDoor())
        {
            if (setVisibilityIfBigger(litTile, newIntensity / 150)) raytracedTiles.add(litTile);
            return;
        }

        if (setVisibilityIfBigger(litTile, newIntensity / 100)) raytracedTiles.add(litTile);
        sendRays(litTile, dir, newIntensity, fadeMultiplier);
    }

    private boolean setVisibilityIfBiggerThanInComparedSet(Tile litTile, double visibility, Set<Tile> comparedSet)
    {
        if (comparedSet.stream().noneMatch(tile -> tile.equals(litTile)) || (visibility > litTile.getVisibility()))
        {
            litTile.setVisibility(visibility);
            return true;
        }
        return false;
    }

    private boolean setVisibilityIfBigger(Tile litTile, double newVisibility)
    {
        boolean isAlreadyInLitCircle = circleLitTiles.stream().anyMatch(tile -> tile.equals(litTile));
        boolean isAlreadyInRayTracedSet = raytracedTiles.stream().anyMatch(tile -> tile.equals(litTile));

        if (isAlreadyInLitCircle || isAlreadyInRayTracedSet)
        {
            if (newVisibility > litTile.getVisibility())
            {
                litTile.setVisibility(newVisibility);
                return true;
            }
            return false;
        }
        else
        {
            litTile.setVisibility(newVisibility);
            return true;
        }
    }
}
