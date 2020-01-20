**Nom	:** Créer un client.

**Résumé :**	Permet à Bill de saisir les coordonnées des clients.

**Acteurs	:** Bill.

**Pré-conditions :**	Le client ne doit pas exister dans le système ((1)).

**Description	:** YAPS veut saisir les coordonnées de ses clients. Elle désire avoir les données figurant sur ses fiches bristol. Les coordonnées des clients sont les suivantes :
* Customer Id : identifiant unique du client ((1)). Cet identifiant est construit manuellement par Bill à partir du nom de famille suivi d'un numéro.
* First Name : prénom
* Last Name : nom de famille
* Telephone : numéro de téléphone où l'on peut joindre le client
* Street 1 et Street 2 : ces deux zones permettent de saisir l'adresse du client.
* City : ville de résidence
* State : état de résidence (uniquement pour les clients américains)
* Zipcode : code postal
* Country : pays de résidence

Uniquement les champs Customer Id, First Name et Last Name sont obligatoires ((2))

**Exceptions :**	((1)) Si l'identifiant saisi existe déjà dans le système, une exception doit être levée.
((2)) Si l'un des champs est manquant, une exception doit être levée.
((GLOBAL)) Si une erreur système se produit, une exception doit être levée.

**Post-conditions:**	Un client est créé.