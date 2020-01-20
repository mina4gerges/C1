Nom:	Mettre à jour les informations d'un client.

Résumé:	Permet à Bill de modifier les coordonnées d'un client.

Acteurs:	Bill.

Pré-conditions:	Le client doit exister dans le système ((1)).

Description:	A partir d'un numéro de client (Customer Id) le système affiche ses coordonnées et propose à Bill de les modifier. Toutes les données sont modifiables sauf l'identifiant. Lors d'une mise à jour, le prénom (First Name) et le nom (Last Name) doivent rester obligatoires ((2)).

Exceptions:	((1)) Si l'identifiant n'existe pas dans le système, une exception doit être levée.
((2)) Si l'un des champs est manquant, une exception doit être levée.
((GLOBAL)) Si une erreur système se produit, une exception doit être levée.

Post-conditions:	Les coordonnées du client sont mises à jour.
