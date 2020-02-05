# Autour de Java PetStore
Java PetStore est une application J2EE que Sun a créé en mai 2001 pour son programme de
Blueprints. C'est un site web marchand où l'on peut choisir des animaux domestiques, les rajouter
dans un panier, puis payer électroniquement. Ce Blueprint a permis de documenter les meilleures
pratiques (code java, design pattern, architecture) pour développer une application J2EE. Le [Java PetStore](petStore.md) est aussitôt devenu un standard de fait puisque les constructeurs de serveur
d'application l'ont utilisé pour démontrer la compatibilité de leur produit avec les spécifications
J2EE. 
En fait, Sun (puis Oracle) fut le premier à l'utiliser pour ses tests de montée en charge. Bien que Java
PetStore ait été développé à des fins éducatives, Oracle déclara que cette application fonctionnait
deux fois plus rapidement sur son serveur d'application que sur ceux de BEA ou IBM. La
communauté s'enflamma et tous les vendeurs commencèrent à utiliser le [PetStore](petStore.md) pour
démontrer leurs meilleures performances.

En savoir plus : [PetStore](petStore.md)

# Introduction (Par Antonio Goncalves avec adaptation)
Comme beaucoup de personnes, aujourd hui, une des meilleures plate-forme de développement pour les applications d'entreprises est J2EE. Elle combine les avantages du langage Java avec les leçons acquises dans le développement depuis ces 10 à 20 dernières années. Elle bénéficie aussi du dynamisme des communautés Open Source ainsi que du JCP de Oracle  ([Java Community Process, processus utilisé par Oracle et de nombreux partenaires pour gérer les évolutions de java et de ces
API](https://jcp.org/en/home/index)).
Bien que prédit à un bel avenir, les promesses de cette plate-forme ne sont pas toujours honorées. Les systèmes délivrés sont souvent trop lents et compliqués, et le temps de développement est, quant à lui, fréquemment disproportionné par rapport à la complexité des demandes utilisateurs.
Pourquoi ? Parce que J2EE est souvent mal utilisé. Les publications faites sur cette plate-forme se
concentrent plus sur les spécifications de J2EE que sur son utilisation réelle. Les problèmes réels
des architectes ou des développeurs sont souvent ignorés.
Pour palier à ce manque, Oracle (et avant SUN) a développé plusieurs Blueprints. Ce sont des documents (exemple de
code, conseils, design patterns, FAQ, ...) qui ont pour but de faciliter le développement
d'applications sur différentes plates-formes. L'un d'eux, appelé [Java PetStore](https://www.oracle.com/technetwork/java/index-136650.html), modélise un site
web marchand où les clients peuvent acheter des animaux domestiques. Ce site est devenu si
populaire que les divers fournisseurs J2EE l'utilisent pour démontrer que leur produit répond bien
aux spécifications. Malheureusement, bien que reconnu par les fournisseurs, développeurs et architectes J2EE, le Java
PetStore est une application complexe, puisque très riche : elle couvre la totalité des spécifications
J2EE et utilise plusieurs design patterns. De plus, créée en mai 2001, elle a dû évoluer pour suivre
les changements des spécifications, la rendant de plus en plus difficile à suivre par la communauté
Java.
Cet ensemble de TPs se propose de vous aider à redévelopper la quasi-totalité de l'application Java PetStore
de manière pédagogique, en se basant sur une approche « problème » plutôt que technologique. En
effet, les technologies ne servent à rien si elles n'ont pas une utilité réelle. Le choix d une
technologie est conditionné par un problème fonctionnel (« mon système a besoin d échanger des
données avec un partenaire qui utilise XML ») ou des contraintes de sécurité (« mon application doit
être sécurisée par un mot de passe et échanger des données cryptées »), de montée en charge (« mon
site web est visité par plus de mille personnes par heure »), de disponibilité (« mon site doit être
accessible 24/7 »), de distribution (« le système de mon partenaire financier se trouve en Asie »). Cet ouvrage vous guidera au travers de la vie mouvementée de la société YAPS (Yet Another
PetStore) qui vend des animaux domestiques par catalogue et qui décide d informatiser petit à petit
son système, au gré de ses besoins. Partant de demandes simples comme « j'ai besoin
d informatiser ma gestion de clients » avec comme contrainte « je n ai ni réseau, ni base de données
et ne possède que des vieux PC », vous arriverez à des demandes utilisateurs beaucoup plus
ambitieuses comme « ma société ayant doublé son chiffre d affaires depuis la création du système
d achat en ligne, je dois faire face à une forte demande de mes clients qui m achètent des produits
du monde entier ». Ce système se nomme YAPS PetStore. Cette approche pédagogique s est un peu inspirée de XP. L eXtreme Programming est un ensemble
de pratiques qui couvrent une grande partie des activités de la réalisation d'un logiciel : de la
planification du projet au développement à proprement dit, en passant par les relations avec le client 
ou l'organisation de l'équipe. Les points suivants ont été pris en compte dans la méthode d'enseignement :
* Les développements sont itératifs. Modéliser une application n'est pas une activité linéaire, il s'agit d'une tâche très complexe, qui nécessite une approche itérative. Dans une itération, on se concentre uniquement sur le développement des fonctionnalités demandées par l'utilisateur. Une fois ces fonctionnalités livrées, on repart sur une nouvelle itération qui
répondra à de nouveaux besoins ou viendra corriger un bug. 
* Être proche des besoins utilisateurs. Les méthodes agiles préconisent d'avoir le client, ou un représentant, à ses
côtés. Ce n'est, bien entendu, pas notre cas, mais les besoins utilisateurs ont cependant une place cruciale dans le cycle de développement tout au long de ce livre. 
* Une fois l application livrée au client (ici la société YAPS), elle peut être utilisable de suite. Avec des cycles d itération court, l application pourra être livrée rapidement et permettre au
client de l utiliser au quotidien. Ceci évite les « effets tunnel », c'est-à-dire lorsque les
analystes et les développeurs passent des mois sans aucun contact avec les utilisateurs et
délivrent alors une application qui ne répond plus à leurs besoins.
* Simplicité. Les méthodes agiles prônent le développement de la chose la plus simple qui puisse fonctionner (the
simplest thing that could possibly work). Cela permet de rester proche des demandes
utilisateurs sans perdre de temps à développer des fonctionnalités qui ne seront jamais
utilisées (You Ain't Gonna Need It).
* Pour qu'un projet puisse itérer dans de bonnes conditions, il lui faut des retours
utilisateurs (feedback). Cela veut dire que durant le développement de l application, les
utilisateurs font des remarques afin d améliorer l application et être sûr qu elle répondra
bien à leurs besoins. Une fois de plus, ce n est pas possible dans notre cas, alors nous nous
appuierons sur des plates-formes de test (xUnit) qui viendront confirmer les besoins
utilisateurs en faisant office de test de recette.
* Remanier (refactoring) le code existant pour qu il s'intègre facilement avec les nouveautés fonctionnelles et technologiques.

# Java PetStore
Java PetStore est une application J2EE que Sun a créé en mai 2001 pour son programme de
Blueprints. C est un site web marchand où l on peut choisir des animaux domestiques, les rajouter
dans un panier, puis payer électroniquement. Ce Blueprint a permis de documenter les meilleures
pratiques (code java, design pattern, architecture) pour développer une application J2EE. Le Java PetStore est aussitôt devenu un standard de fait puisque les constructeurs de serveur
d application l ont utilisé pour démontrer la compatibilité de leur produit avec les spécifications
J2EE. En fait, Oracle fut le premier à l utiliser pour ces tests de montée en charge. Bien que Java
PetStore ait été développé à des fins éducatives, Oracle déclara que cette application fonctionnait
deux fois plus rapidement sur son serveur d application que sur ceux de BEA ou IBM. La
communauté s enflamma et tous les vendeurs commencèrent à utiliser le Java PetStore pour
démontrer leurs meilleures performances.