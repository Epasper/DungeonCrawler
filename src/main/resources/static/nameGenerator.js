// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// name_generator.js
// written and released to the public domain by drow <drow@bin.sh>
// http://creativecommons.org/publicdomain/zero/1.0/

var name_set = {};
var chain_cache = {};

name_set['celtic'] = [
    'Aberth', 'Abhartach', 'Abhean',
    'Ablach', 'Acaunus', 'Acco',
    'Adcoprovatus', 'Addedomarus', 'Adgennus',
    'Adhna', 'Adhnuall', 'Adminius',
    'Adomnan', 'Adwen', 'Aedan',
    'Aelchinn', 'Aer', 'Aesico',
    'Aesk', 'Aesubilinus', 'Agh',
    'Agned', 'Agnoman', 'Agulus',
    'Aherne', 'Aiel', 'Ailbhe',
    'Ailell', 'Ailill', 'Aillen',
    'Aillinn', 'Aincel', 'Aindelbadh',
    'Aine', 'Ainle', 'Ainmire',
    'Ainsel', 'Ainvar', 'Airard',
    'Airetach', 'Airnelach', 'Airt',
    'Albanach', 'Albarnaid', 'Alcuin',
    'Allobrogicus', 'Alpin', 'Alston',
    'Aluin', 'Alun', 'Amalgoid',
    'Ambicatos', 'Ambiorix', 'Amergin',
    'Amgerit', 'Amlaibh', 'Amulgo',
    'Andala', 'Andela', 'Andesasus',
    'Andragius', 'Androgius', 'Aneroestus',
    'Anlaf', 'Antedios', 'Aouen',
    'Apullio', 'Arbell', 'Arcallach',
    'Archil', 'Archu', 'Ardan',
    'Argentocoxos', 'Argentocoxus', 'Arias',
    'Ariomardus', 'Ariovistus', 'Arontuis',
    'Arranen', 'Art', 'Artbranan',
    'Artgal', 'Arthgal', 'Arthmail',
    'Artigan', 'Artrach', 'Artri',
    'Arverus', 'Arviragus', 'Asal',
    'Ascatinius', 'Atepacius', 'Attus',
    'Audagus', 'Auisle', 'Aulay',
    'Aurog', 'Autaritus', 'Avitus',
    'Bacauda', 'Baclan', 'Baculo',
    'Badvoc', 'Baethbarr', 'Baiscne',
    'Baithan', 'Baithen', 'Baithene',
    'Balor', 'Banquerius', 'Banquo',
    'Barloc', 'Barnoc', 'Baroc',
    'Baruch', 'Bathan', 'Beag',
    'Becuma', 'Bedwyr', 'Belatucader',
    'Bellicia', 'Bellicianus', 'Bellovesus',
    'Belu', 'Beolain', 'Berchan',
    'Berec', 'Beric', 'Bericus',
    'Bernech', 'Berngal', 'Berric',
    'Bersa', 'Betach', 'Bhuice',
    'Bicelmos', 'Bilis', 'Biorach',
    'Bitucus', 'Bitudacus', 'Blaan',
    'Blathmac', 'Blathmec', 'Bleddfach',
    'Blescius', 'Bloc', 'Boann',
    'Boant', 'Bobd', 'Bodenius',
    'Bodh', 'Bodhe', 'Bodiccius',
    'Bodugenus', 'Boduogenus', 'Bodvoc',
    'Bodvogenus', 'Bogitarus', 'Boisel',
    'Boisil', 'Bonoxus', 'Borba',
    'Bothan', 'Bov', 'Brach',
    'Bragon', 'Bran', 'Brancus',
    'Brannoc', 'Brath', 'Breagan',
    'Breasal', 'Brecbrennoch', 'Brelade',
    'Brennus', 'Breogan', 'Bres',
    'Bresal', 'Breward', 'Briavel',
    'Bricriu', 'Bricrue', 'Brieuc',
    'Brigaco', 'Brigantius', 'Brigia',
    'Briginus', 'Brigomaglos', 'Brioc',
    'Britt', 'Broccan', 'Brockmail',
    'Broderick', 'Brogus', 'Broichan',
    'Brucetius', 'Brude', 'Bruide',
    'Bruidge', 'Bruscius', 'Brychanus',
    'Brys', 'Bryth', 'Buan',
    'Buccus', 'Buda', 'Budocesuganios',
    'Buic', 'Buichet', 'Buite',
    'Cabrach', 'Cabriabanus', 'Cacumattus',
    'Cadwan', 'Caedmac', 'Cael',
    'Caenneth', 'Caibre', 'Caichear',
    'Caier', 'Cailcheir', 'Caince',
    'Caincenn', 'Cainnelscaith', 'Cainte',
    'Cairbre', 'Cairbri', 'Cais',
    'Caisel', 'Caitchenn', 'Caittil',
    'Calgacus', 'Calphurn', 'Camel',
    'Camulacus', 'Candiedo', 'Cannaid',
    'Caoilte', 'Capell', 'Caractacus',
    'Caradig', 'Caratach', 'Carbery',
    'Carbh', 'Carell', 'Carpre',
    'Cartivellaunos', 'Carvilius', 'Cas',
    'Cascorach', 'Cassal', 'Cassavus',
    'Cassivellaunus', 'Cassobellaunus', 'Catavignus',
    'Cathail', 'Cathal', 'Cathan',
    'Cathba', 'Cathbadh', 'Cathlan',
    'Cathman', 'Catigern', 'Catiotuos',
    'Cattigern', 'Catualda', 'Cavarinus',
    'Cé', 'Cealaigh', 'Ceallach',
    'Ceanatis', 'Ceannmhair', 'Cearnach',
    'Cecht', 'Ceithin', 'Celatus',
    'Cellach', 'Celtchar', 'Celtillus',
    'Cenau', 'Cerball', 'Cerd',
    'Cerebig', 'Ceretic', 'Cermait',
    'Cerotus', 'Cesarn', 'Cet',
    'Cethern', 'Cett', 'Ciabhan',
    'Ciach', 'Cian', 'Cicht',
    'Cimarus', 'Cinaed', 'Cingetorix',
    'Cinhil', 'Cintugnatus', 'Cintusmus',
    'Ciotha', 'Ciothruadh', 'Cistumucus',
    'Cithruadh', 'Clanova', 'Cliach',
    'Clonard', 'Cluim', 'Cobthach',
    'Cochlan', 'Codal', 'Codhna',
    'Coemgen', 'Cogidubnos', 'Cogidubnus',
    'Cogitosus', 'Coimhleathan', 'Coinmagil',
    'Coinmail', 'Coinneach', 'Coirpre',
    'Colasunius', 'Colban', 'Colga',
    'Coll', 'Colla', 'Collamair',
    'Collbrain', 'Colles', 'Colm',
    'Colmkill', 'Colpa', 'Colum',
    'Comgal', 'Comgall', 'Comgan',
    'Comhrag', 'Comitinus', 'Comman',
    'Commius', 'Compar', 'Comrith',
    'Comur', 'Comux Con',
    'Conaing', 'Conaire', 'Conairy',
    'Conal', 'Conall', 'Conan',
    'Conaran', 'Concolitanus', 'Conconnetodumnos',
    'Concuing', 'Condidan', 'Conmail',
    'Connachtach', 'Connell', 'Connla',
    'Connor', 'Conor', 'Conory',
    'Conuall', 'Copillus', 'Coplait',
    'Coran', 'Corann', 'Corb',
    'Corc', 'Corfil', 'Corin',
    'Corio', 'Cormiac', 'Coron',
    'Corotiacus', 'Corpry', 'Corrgenn',
    'Cospatrick', 'Costicus', 'Cotuatus',
    'Covac', 'Craftiny', 'Credne',
    'Crega', 'Crico', 'Cridenbel',
    'Crimall', 'Crimthan', 'Crimthann',
    'Criomnal', 'Crocus', 'Crom',
    'Crotus', 'Crovan', 'Cruithne',
    'Crunnagh', 'Crunnchu', 'Cu',
    'Cuadan', 'Cuailgne', 'Cuaillemech',
    'Cualann', 'Cuano', 'Cuchulainn',
    'Cuiline', 'Cuill', 'Cuiran',
    'Cuirithir', 'Culain', 'Culcaigrie',
    'Culhwch', 'Cullen', 'Cumhaill',
    'Cumhal', 'Cumhall', 'Cummain',
    'Cuneda', 'Cunedda', 'Cuneglasus',
    'Cunetio', 'Cunittus', 'Cunlinc',
    'Cunoarda', 'Cunobarros', 'Cunobarrus',
    'Cunobelin', 'Cunobelinus', 'Cunomaglus',
    'Cunopectus', 'Cunori', 'Cunorix',
    'Cunotamus', 'Cunoval', 'Cunovindus',
    'Cur', 'Curatio', 'Curmissus',
    'Curoi', 'Cushling', 'Cuthlyn',
    'Cynloyp', 'Cynran', 'Cyrnan',
    'Dagobitus', 'Daich', 'Daighre',
    'Daigre', 'Daire', 'Dalbaech',
    'Dalbh', 'Dall', 'Dannicus',
    'Dathi', 'Deaghadh', 'Dearc',
    'Dearmid', 'Debrann', 'Decheall',
    'Dedidach', 'Deglain', 'Delbaith',
    'Demna', 'Derc', 'Derca',
    'Dergcroche', 'Dergdian', 'Dering',
    'Desa', 'Detha', 'Dian',
    'Dian-Cet', 'Diarmaid', 'Diarmait',
    'Diarmid', 'Dichu', 'Digbail',
    'Dill', 'Dinogad', 'Diocain',
    'Diorraing', 'Diovicus', 'Diviciacus',
    'Dobar', 'Doccius', 'Dogfael',
    'Doinus', 'Dolar', 'Dolb',
    'Doli', 'Domhar', 'Domingart',
    'Domnann', 'Domnoellaunus', 'Donnarthadh',
    'Donn-Ruadh', 'Dornoch', 'Dorus',
    'Drecan', 'Drem', 'Dremen',
    'Driccius', 'Driumne', 'Drochmail',
    'Drostan', 'Druim', 'Druimderg',
    'Drust', 'Drustic', 'Drystan',
    'Duach', 'Duane', 'Duartane',
    'Duatha', 'Dubh', 'Dubhacon',
    'Dubhan', 'Dubhdaleithe', 'Dubhgall',
    'Dubhlaing', 'Dubnovellaunus', 'Dubnowalos',
    'Dubnus', 'Dudoc', 'Dufan',
    'Dufgal', 'Duftah', 'Dugald',
    'Dumnail', 'Dumnocoverus', 'Dumnogenus',
    'Dumnorix', 'Dumnove', 'Dumnovellaunus',
    'Dunegal', 'Dunegall', 'Dunmail',
    'Dunocratis', 'Dunod', 'Duthac',
    'Eab', 'Eachaidh', 'Easal',
    'Eathfaigh', 'Eber', 'Ebicatos',
    'Eborius', 'Eburos', 'Echaid',
    'Ecimius', 'Ecne', 'Eidirsgul',
    'Eimhir', 'Eine', 'Eisu',
    'Eithis', 'Elagabalus', 'Elaphius',
    'Elatha', 'Elathan', 'Elbodugus',
    'Elcmair', 'Eldad', 'Elitovius',
    'Elkmar', 'Elvod', 'Elvodug',
    'Eman', 'Emi', 'Emmass',
    'Enchered', 'Enda', 'Enemnogenus',
    'Enestinus', 'Eoban', 'Eochaid',
    'Eocho', 'Eochy', 'Eochymac',
    'Eogabil', 'Eogan', 'Eoganan',
    'Eoghan', 'Eolus', 'Eparchius',
    'Epaticcu', 'Epaticcus', 'epatticus',
    'Epillicus', 'Eppillus', 'Erc',
    'Eremon', 'Erp', 'Err',
    'Ervic', 'Esca', 'Espaid',
    'Esunertus', 'Etain', 'Etar',
    'Etarlaim', 'Eterskel', 'Etgall',
    'Ethain', 'Ethaman', 'Eunan',
    'Evicatos', 'Facha-Muilleathan', 'Faelinn',
    'Faltlaba', 'Faolan', 'Farinmagil',
    'Farinmail', 'Febal', 'Feclach',
    'Fedlimidh', 'Feidlim', 'Feinn',
    'Felim', 'Ferai', 'Fercos',
    'Ferdia', 'Ferdiad', 'Ferdoman',
    'Fermaise', 'Fertai', 'Fertuinne',
    'Festinien', 'Ffion', 'Fiacha',
    'Fiachna', 'Fiachu', 'Fiacuil',
    'Ficna', 'Figel', 'Figol',
    'Finan', 'Finched', 'Findabair',
    'Findemas', 'Findgoll', 'Findlaech',
    'Finegas', 'Finn', 'Finnaistucan',
    'Finnan', 'Finnbane', 'Finnbennach',
    'Finnian', 'Finnleik', 'Fintain',
    'Finvel', 'Fiodhaidh', 'Fitheal',
    'Flain', 'Flann', 'Flannan',
    'Flidias', 'Fochlann', 'Foich',
    'Foilleán', 'Foiranach', 'Fola',
    'Follamain', 'Forannán', 'Forannen',
    'Forne', 'Fothaid', 'Fotla',
    'Fuad', 'Fufidius', 'Fullon',
    'Fursa', 'Gaible', 'Gallgoid',
    'Gamal', 'Garad', 'Garbhcronan',
    'Garraidh', 'Gartnait', 'Garva',
    'Garwin', 'Garym', 'Gault',
    'Gavin', 'Gebann', 'Germocus',
    'Geron', 'Getorix', 'Gilla',
    'Gillaciaran', 'Gillacomghain', 'Gillechrist',
    'Gillibride', 'Gillicolm', 'Gillocher',
    'Gilloman', 'Gingomarus', 'Giolla-Caeimhghin',
    'Glas', 'Glasan', 'Glein',
    'Glentilt', 'Glore', 'Gnathach',
    'Gnobeg', 'Gnomor', 'Gobannitio',
    'Gobhan', 'Godebog', 'Goden',
    'Godfraidh', 'Goineach', 'Goitne',
    'Goll', 'Gorm-Shuileach', 'Gorthyn',
    'Gospatrick', 'Gothan', 'Gourchien',
    'Govan', 'Graeme', 'Graham',
    'Grannus', 'Gretorix', 'Grian',
    'Grummoch', 'Guan', 'Guern',
    'Gugein', 'Guitolinus', 'Guoruoe',
    'Guotepauc', 'Guthar', 'Guthor',
    'Gwythno', 'Hanesa', 'Hanlon',
    'Hanno', 'Heber', 'Heremon',
    'Huil', 'Hunno', 'Idanach',
    'Iduthin', 'Iehmarc', 'Igalram',
    'Ilar', 'Ilaunos', 'Ilbrec',
    'Ilbrech', 'Ildathach', 'Imhar',
    'Imidd', 'Indech', 'Indrechtach',
    'Indutioamrus', 'Indutius', 'Ingcel',
    'Ingnathach', 'Ingol', 'Innel',
    'Innsa', 'Invomandus', 'Iogenan',
    'Iollen', 'Iolunn', 'Ir',
    'Irdun', 'Istolatius', 'Istoreth',
    'Ith', 'Iubdan', 'Iuchar',
    'Iucharba', 'Iunsa', 'Ivomagus',
    'Ivonercus', 'Jutus', 'Kane',
    'Keir', 'Kentigern', 'Kenulphus',
    'Kian', 'Kilian', 'Kinan',
    'Kinemark', 'Kineth', 'Kinnear',
    'Kolbein', 'Kuno', 'Kylan',
    'Kyndylan', 'Labra', 'Labran',
    'Lachlan', 'Laeg', 'Laegaire',
    'Laeghairé', 'Laery', 'Laethrig',
    'Lainbhui', 'Lairgnen', 'Lairgren',
    'Laisren', 'Lanuccus', 'Latharne',
    'Leffius', 'Leire', 'Leith',
    'Leslie', 'Lethan', 'Levin',
    'Lewy', 'Lia', 'Liagan',
    'Liath', 'Liathain', 'Liathan',
    'Lifecar', 'Lindores', 'Liobhan',
    'Lir', 'Litaviccus', 'Litugenus',
    'Llif', 'Llud', 'Loarn',
    'Loarne', 'Lobais', 'Loban',
    'Lobharan', 'Lobos', 'Logiore',
    'Logotorix', 'Lomna', 'Lon',
    'Lorcain', 'Lossio', 'Lousius',
    'Lovernianus', 'Luachaid', 'Luachair',
    'Lubrin', 'Lucco', 'Luchta',
    'Luchtar', 'Luctacus', 'Luel',
    'Luga', 'Lugaidh', 'Lughaid',
    'Lugobelinus', 'Lugotorix', 'Lugovalos',
    'Luibra', 'Luloecen', 'Lyfing',
    'Lynch', 'Mac Da Tho', 'Macbeathach',
    'Macbeathad', 'Macer', 'Machar',
    'Machute', 'Macnia', 'Madach',
    'Madan', 'Maddan', 'Madduin',
    'Mador', 'Maeduin', 'Mael',
    'Maelbeth', 'Maelchwn', 'Maeldun',
    'Maelgan', 'Maelgwn', 'Maelinmhain',
    'Maelmadoc', 'Maelmichael', 'Maelmuir',
    'Maelmuire', 'Maelnibha', 'Maelochtair',
    'Maelochtar', 'Maelrubai', 'Maelrubha',
    'Maelsechlainn', 'Maelsechnaill', 'Maeltine',
    'Maglocunus', 'Maglorix', 'Maieul',
    'Mailchon', 'Maine', 'Mal',
    'Malbride', 'Maldred', 'Malduin',
    'Malliacus', 'Malone', 'Malpedar',
    'Malpedur', 'Malride', 'Mamos',
    'Mandubracius', 'Manducios', 'Mannig',
    'Maol', 'Maon', 'Mar',
    'Marbod', 'Maredoc', 'Marobodunus',
    'Martacus', 'Maslorius', 'Mathgen',
    'Matuacus', 'Matugenus', 'Maturus',
    'Meardha', 'Meargach', 'Mechi',
    'Medraut', 'Mellonus', 'Melmor',
    'Menua', 'Merddyn', 'Mhaolain',
    'Mhichil', 'Miach', 'Michan',
    'Midac', 'Mide', 'Midhé',
    'Midhir', 'Midhna', 'Midir',
    'Mil', 'Miled', 'Milucra',
    'Miochaoin', 'Miodac', 'Miorog',
    'Mochrum', 'Mochta', 'Mochuda',
    'Modhaarn', 'Modomnoc', 'Moengal',
    'Molacus', 'Molaise', 'Molloy',
    'Moluag', 'Monaid', 'Moncuxoma',
    'Mongan', 'Morann', 'Morc',
    'Morgund', 'Moriartak', 'Morias',
    'Moricamulus', 'Morirex', 'Moritasgus',
    'Morvidd', 'Motius', 'Muadhan',
    'Muddan', 'Muirchú', 'Muiredach',
    'Mungo', 'Murchadh', 'Mutaten',
    'Muthill', 'Nadfraech', 'Naid',
    'Nantosvelta', 'Nantua', 'Naoise',
    'Narlos', 'Natanleod', 'Nathrach',
    'Natorus', 'Neamh', 'Necalimes',
    'Nechtan', 'Nectovelius', 'Neidhe',
    'Neit', 'Nemanach', 'Nemed',
    'Nemglan', 'Nemhnain', 'Nemmonius',
    'Nessa', 'Niadhnair', 'Nollaig',
    'Nos', 'Novantico', 'Nuada',
    'Nuadha', 'Nynia', 'Octrialach',
    'Octruil', 'Odhrain', 'Odras',
    'Ogma', 'Ogmios', 'Oilioll',
    'Oisin', 'Olchobar', 'Ollovico',
    'Oncus', 'Orbissa', 'Oren',
    'Orgetorix', 'Orgillus', 'Orphir',
    'Oscair', 'Oscar', 'Osgar',
    'Owain', 'Paetus', 'Patendinus',
    'Pesrut', 'Pisear', 'Potitus',
    'Potomarus', 'Prasutagus', 'Pridfirth',
    'Qodvoldeus', 'Raighne', 'Raigre',
    'Rascua', 'Regol', 'Reoda',
    'Reo-Derg', 'Rhiada', 'Riagall',
    'Rian', 'Rianorix', 'Riata',
    'Ribh', 'Ringan', 'Riommar',
    'Rivius', 'Robartaig', 'Robhartach',
    'Rogh', 'Roth', 'Rowan',
    'Ruadan', 'Ruadhan', 'Rudrach',
    'Rudraighe', 'Ruide', 'Ruith',
    'Rurio', 'Saccius', 'Saenius',
    'Saenu', 'Saidhe', 'Sal',
    'Salmhor', 'Salorch', 'Saltran',
    'Samtan', 'Samthainn', 'Sangus',
    'Saturio', 'Sawan', 'Sceolan',
    'Scrocmagil', 'Scrocmail', 'Seaghan',
    'Seanchab', 'Searigillus', 'Searix',
    'Sechnaill', 'Secumos', 'Sedullus',
    'Seghine', 'Segine', 'Segovax',
    'Sellic', 'Semion', 'Senaculus',
    'Sencha', 'Senias', 'Sennianus',
    'Senorix', 'Senshenn', 'Senuacus',
    'Sepenestus', 'Sera', 'Servan',
    'Sesnan', 'Setanta', 'Setibogius',
    'Sgoith-Gleigeil', 'Sharvan', 'Sholto',
    'Sighi', 'Sigmal', 'Silinus',
    'Sinell', 'Sinill', 'Sinnoch',
    'Sinsar', 'Sital', 'Sitric',
    'Skolawn', 'Sligech', 'Smertrius',
    'Solais', 'Sollus', 'Sorio',
    'Soulinus', 'Sreng', 'Stariat',
    'Starn', 'Stavacus', 'Strathairn',
    'Strowan', 'Struan', 'Suadnus',
    'Sualdam', 'Sualtam', 'Suanach',
    'Suavis', 'Subhkillede', 'Subsio',
    'Sucabus', 'Suibhne', 'Sulien',
    'Summacus', 'Suriacus', 'Syagris',
    'Tabarn', 'Tadg', 'Tah-Nu',
    'Tailc', 'Taileach', 'Taistellach',
    'Talchimen', 'Taliesin', 'Talorcan',
    'Talore', 'Tamesubugus', 'Tammonius',
    'Tarvos', 'Tasciovanus', 'Tasgetius',
    'Tassach', 'Taximagulus', 'Tethra',
    'Tetrecus', 'Tetricus', 'Teutomatus',
    'Teyrnon', 'Tigernach', 'Tigernann',
    'Tincomarus', 'Tincommius', 'Tocha',
    'Togodumnus', 'Topa', 'Tor',
    'Torannen', 'Toutius', 'Trad',
    'Tradui', 'Trendhorn', 'Trenmor',
    'Trenus', 'Treon', 'Triathmor',
    'Trogain', 'Troghwen', 'Tuaigh',
    'Tuan', 'Tuathal', 'Tuirbe',
    'Tuireann', 'Tuis', 'Tullich',
    'Turenn', 'Turlough', 'Tyrnon',
    'Uaithnin', 'Uar', 'Uccus',
    'Uchtain', 'Ueda', 'Uepogenus',
    'Uige', 'Uirolec', 'Uisneach',
    'Ulchil', 'Un', 'Unthaus',
    'Urfai', 'Urgriu', 'Urias',
    'Urien', 'Usna', 'Usnach',
    'Vadrex', 'Vainche', 'Vallaunius',
    'Vassedo', 'Vatiaucus', 'Veda',
    'Vediacus', 'Vellocatus', 'Veluvius',
    'Venutius', 'Vepogenus', 'Vercassivellaunus',
    'Vercingetorix', 'Verctissa', 'Verecundus',
    'Verica', 'Vernico', 'Viasudrus',
    'Viducus', 'Vindex', 'Vindomorucius',
    'Vinnian', 'Virdomarus', 'Viroma',
    'Virssucius', 'Volisus', 'Vortigern',
    'Vortimax', 'Vortimer', 'Vortipor',
    'Vortrix', 'Voteporix', 'Vran',
    'Vron', 'Wannard', 'Weonard'
]

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// generator function

function addNameToField(fieldId) {
    let selectedElement = document.getElementById(fieldId);
    const generatedName = generate_name('celtic');
    selectedElement.value = generatedName;
    debugger;
}



function generate_name(type) {
    var chain; if (chain = markov_chain(type)) {
        return markov_name(chain);
    }
    return '';
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// generate multiple

function name_list(type, n_of) {
    var list = [];

    var i; for (i = 0; i < n_of; i++) {
        list.push(generate_name(type));
    }
    return list;
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// get markov chain by type

function markov_chain(type) {
    var chain; if (chain = chain_cache[type]) {
        return chain;
    } else {
        var list; if (list = name_set[type]) {
            var chain; if (chain = construct_chain(list)) {
                chain_cache[type] = chain;
                return chain;
            }
        }
    }
    return false;
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// construct markov chain from list of names

function construct_chain(list) {
    var chain = {};

    var i; for (i = 0; i < list.length; i++) {
        var names = list[i].split(/\s+/);
        chain = incr_chain(chain, 'parts', names.length);

        var j; for (j = 0; j < names.length; j++) {
            var name = names[j];
            chain = incr_chain(chain, 'name_len', name.length);

            var c = name.substr(0, 1);
            chain = incr_chain(chain, 'initial', c);

            var string = name.substr(1);
            var last_c = c;

            while (string.length > 0) {
                var c = string.substr(0, 1);
                chain = incr_chain(chain, last_c, c);

                string = string.substr(1);
                last_c = c;
            }
        }
    }
    return scale_chain(chain);
}
function incr_chain(chain, key, token) {
    if (chain[key]) {
        if (chain[key][token]) {
            chain[key][token]++;
        } else {
            chain[key][token] = 1;
        }
    } else {
        chain[key] = {};
        chain[key][token] = 1;
    }
    return chain;
}
function scale_chain(chain) {
    var table_len = {};

    var key; for (key in chain) {
        table_len[key] = 0;

        var token; for (token in chain[key]) {
            var count = chain[key][token];
            var weighted = Math.floor(Math.pow(count, 1.3));

            chain[key][token] = weighted;
            table_len[key] += weighted;
        }
    }
    chain['table_len'] = table_len;
    return chain;
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// construct name from markov chain

function markov_name(chain) {
    var parts = select_link(chain, 'parts');
    var names = [];

    var i; for (i = 0; i < parts; i++) {
        var name_len = select_link(chain, 'name_len');
        var c = select_link(chain, 'initial');
        var name = c;
        var last_c = c;

        while (name.length < name_len) {
            c = select_link(chain, last_c);
            name += c;
            last_c = c;
        }
        names.push(name);
    }
    return names.join(' ');
}
function select_link(chain, key) {
    var len = chain['table_len'][key];
    var idx = Math.floor(Math.random() * len);

    var t = 0; for (token in chain[key]) {
        t += chain[key][token];
        if (idx < t) { return token; }
    }
    return '-';
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -