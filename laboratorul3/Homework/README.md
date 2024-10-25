# Homework

## 1. Crearea de pagini pentru vizualizarea produselor folosind datatables.

Pagina **products.xhtml**  utilizează biblioteca PrimeFaces pentru a afișa o listă de produse într-un tabel interactiv. \
Tabelul folosește atributul value="#{productView.products}", \
ceea ce înseamnă că lista de produse este obținută dintr-o colecție products definită în productView, un bean de tip Java. \
Parametrul var="product" definește o variabilă locală pentru a accesa atributele fiecărui produs.

## 2. Crearea unei pagini care permite adaugarea sau editarea unui user folosind dialog

Pagina **users.xhtml** definește o structură cu tabelul de utilizatori (p:dataTable), un buton pentru adăugarea unui nou utilizator, \
un dialog pentru editarea și adăugarea unui utilizator, și un dialog pentru selectarea produselor asociate utilizatorului.

# Dialogul pentru Adăugare/Editare Utilizator (p:dialog - userDialog)

Dialogul userDialog este folosit pentru a introduce sau edita datele unui utilizator și include: 

Input text pentru nume (p:inputText): permite introducerea numelui utilizatorului. \
Input text pentru email (p:inputText): câmp obligatoriu pentru email. \
**DatePicker** pentru data nașterii (p:calendar): folosit pentru a selecta data nașterii. \
**SelectOneMenu** pentru gen (p:selectOneMenu): folosește o listă de opțiuni (genders) pentru a selecta genul utilizatorului. \
Checkbox-uri multiple pentru roluri (p:selectManyCheckbox): oferă lista de roluri (availableRoles) pentru a selecta rolurile asociate utilizatorului. 

# PickList

Pentru a permite selectarea produselor asociate unui utilizator, am folosit un p:pickList care \
permite transferul de produse între două liste (listă dublă). Atributele importante sunt: 

**value="#{productEdit.dualListModel}"**: modelul de date utilizat pentru listă. \
**converter="productConverter"**: un convertor folosit pentru a transforma produsele în entități persistente. \
itemLabel și itemValue: eticheta și valoarea fiecărui produs afișat în listă. 

## 3. Definirea unui flux de navigare care permite tranziția între pagini

Am utilizat navigation rules în fișierul de configurare **faces-config.xml**. 
Aceste reguli specifică pagina de destinație în funcție de outcome-ul (rezultatul) returnat de o metodă din bean-ul de tip back-end.

## 4. Internaționalizarea interfetei cu utilizatorul și oferirea de suport pentru cel puțin două localități.

Pentru a oferi suport pentru interfața internaționalizată, am configurat aplicația să gestioneze cel puțin două locații (locales) – engleză și franceză. \
Acest lucru se realizează prin utilizarea pachetelor de resurse de tip resource-bundle, care conțin mesajele traduse în limbile respective, și prin configurarea implicită a limbii în **faces-config.xml**.


