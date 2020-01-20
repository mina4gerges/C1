**Nom:**	Supprimer un client.

**Résumé:**	Permet à Bill de supprimer un client du système.

**Acteurs:**	Bill.

**Pré-conditions:**	Le client doit exister dans le système ((1)).

**Description:**	A partir d'un numéro de client (Customer Id) le système affiche ses coordonnées et propose à Bill de le supprimer. Si Bill accepte alors le client est supprimé du système.

**Exceptions:**	((1)) Si l'identifiant n'existe pas dans le système, une exception doit être levée.
((GLOBAL)) Si une erreur système se produit, une exception doit être levée.

**Post-conditions:**	Le client est supprimé.