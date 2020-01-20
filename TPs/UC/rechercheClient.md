Nom	Rechercher un client par son identifiant.

Résumé	Permet à Bill de rechercher les coordonnées d'un client.

Acteurs	Bill.

Pré-conditions	Le client doit exister dans le système ((1)).

Description	A partir d'un numéro de client (Customer Id) le système affiche ses coordonnées.

Exceptions	((1)) Si l'identifiant n'existe pas dans le système, une exception doit être levée.
((GLOBAL)) Si une erreur système se produit, une exception doit être levée.

Post-conditions	Les coordonnées du client affichées.