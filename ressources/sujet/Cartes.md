# Description des cartes

## Bang!
<img width="100px" alt="Bang!" src="../web/images/cards/bang_2C.png" />

**Détails :** Le joueur doit entrer *le nom du joueur* à attaquer parmi les joueurs à portée d'arme. Si le joueur cible a une protection (*Planque* ou *Jourdonnais*) le tirage est fait automatiquement. S'il n'a pas de protection ou que la protection a échoué, le joueur cible peut défausser une carte *Raté!* de sa main en entrant "`Missed!`" ou passer et subir un point de dégâts.

**Remarques :**
- Un joueur peut jouer une carte *Bang!* même s'il n'y a aucun joueur à portée, sans effet.
- Sauf cas particulier, un joueur ne peut jouer qu'une seule carte *Bang!* par tour.
- *Calamity Janet* peut défausser une carte *Bang!* de sa main en entrant "`Bang!`" à la place d'une carte *Raté!* pour annuler un effet *Bang!* (carte *Bang!* ou *Gatling*).


## Planque (*Barrel*)
<img width="100px" alt="Barrel" src="../web/images/cards/barrel_KS.png" />

**Détails :** Le tirage aléatoire ("dégainer") est exécuté *automatiquement* lorsqu'un joueur qui a une *Planque* est la cible d'un effet *Bang!* (carte *Bang!* ou *Gatling*).


## Bière (*Beer*)
<img width="100px" alt="Beer" src="../web/images/cards/beer_6H.png" />

**Détails :** Si un joueur est tué alors qu'il a assez de cartes *Bière* en main pour rester en vie (et que les cartes *Bière* ont encore un effet), les cartes sont *automatiquement* défaussées de sa main pour lui rendre un point de vie même si ce n'est pas son tour. Si le joueur a reçu plus d'un point de dégât fatal (ex: *Dynamite*) il ne défausse et survit que s'il a suffisamment de cartes *Bière* en main pour remonter à 1 point de vie.

**Remarques :**
- Le nombre de points de vie d'un joueur ne peut pas dépasser son nombre de points de vie maximum (points de vie du personnage, +1 éventuellement si le joueur est le Shériff). Le joueur peut tout de même jouer une carte *Bière* s'il est au maximum, sans effet.
- Les cartes *Bière* n'ont plus d'effet lorsqu'il ne reste plus que deux joueurs en vie dans la partie. Mais les joueurs peuvent tout de même les jouer, sans effet. 


## Coup de foudre (*Cat Balou*)
<img width="100px" alt="Cat Balou" src="../web/images/cards/catbalou_9D.png" />

**Détails :** Le joueur entre d'abord *le nom du joueur* ciblé, puis il entre *le nom de la carte* à défausser parmi celles posées devant la cible, ou il passe pour faire défausser une carte aléatoirement de la main de la cible.

**Remarques :**
- Il est possible de cibler n'importe quel joueur, indépendamment des distances 
- Les armes des joueurs peuvent être défaussées
- Les cartes *Prison* et *Dynamite* posées devant un joueur peuvent également être défaussées
- Il est possible de choisir de faire défausser une carte en main à un joueur qui n'a aucune carte en main, mais dans ce cas la carte n'a aucun effet


## Duel
<img width="100px" alt="Duel" src="../web/images/cards/duel_8C.png" />

**Détails :** Le joueur entre *le nom du joueur* ciblé. Puis à tour de rôle chacun des deux joueurs (en commençant par le joueur ciblé) peut soit défausser une carte *Bang!* de sa main en entrant "`Bang!`" soit passer et subit un point de dégâts.

**Remarques :**
- Les protections (*Planque* et *Jourdonnais* ne s'appliquent pas lors d'un *Duel*)
- *Calamity Janet* peut défausser des cartes *Raté!* de sa main en entrant "`Missed!`" à la place de cartes *Bang!* 


## Dynamite
<img width="100px" alt="Dynamite" src="../web/images/cards/dynamite_2H.png" />

**Détails :** Les tirages aléatoires ("dégainer") de la *Dynamite* sont exécutés *automatiquement* au début du tour d'un joueur qui a une *Dynamite* devant lui. La *Dynamite* est automatiquement déplacée devant le joueur suivant si elle n'explose pas.

**Remarques :**
- Si un joueur meurt alors que la *Dynamite* est posée devant lui, la *Dyanmite* est défaussée
- *Vulture Sam* récupère en main la *Dynamite* si un joueur meurt alors qu'elle est posée devant lui, mais pas si la *Dynamite* tue un joueur en explosant au début de son tour (dans ce cas la *Dynamite* est défaussée avant que le joueur soit tué)
- Si la *Dynamite* explose sur *Bart Cassidy* et qu'il survit, il pioche 3 cartes en main
- *El Gringo* ne pioche pas de cartes s'il subit des dégâts de la *Dynamite* (les dégâts ne sont pas attribués au joueur qui avait posé la *Dynamite*)


## Gatling
<img width="100px" alt="Gatling" src="../web/images/cards/gatling_10H.png" />

**Détails :** Tous les adversaires subissent *automatiquement* un effet *Bang!*. Les protections (*Planque* et *Jourdonnais*) sont jouées automatiquement, et ils peuvent défausser une carte *Raté* en entrant "`Missed!`", comme lorsqu'ils sont ciblés par une carte *Bang!* ou passer et subir 1 point de dégâts. Les actions sont exécutées et résolues dans l'ordre du tour en commençant par le joueur immédiatement après le joueur qui joue la carte *Gatling*.

**Remarques :**
- *Calamity Janet* peut défausser une carte *Bang!* en entrant "`Bang!`" pour éviter les dégâts


## Magasin (*General Store*)
<img width="100px" alt="General Store" src="../web/images/cards/generalstore_9C.png" />

**Détails :** Les cartes sont automatiquement piochées et chaque joueur (dans l'ordre du tour, en commençant par le joueur qui a joué le *Magasin*) doit entrer le nom de la carte qu'il veut prendre (vous pouvez utiliser l'argument `showButtons` de la fonction `Player.chooseCard` pour afficher la liste des cartes disponibles)

## Indiens! (*Indians!*)
<img width="100px" alt="Indians!" src="../web/images/cards/indians_AD.png" />

**Détails :** Chaque adversaire, dans l'ordre du tour en commençant par le joueur immédiatement après le joueur qui a joué la carte, peut défausser un *Bang!* de sa main en entrant "`Bang!`" ou passer et subir un point de dégâts.

**Remarques :**
- *Calamity Janet* peut défausser une carte *Raté!* de sa main à la place d'un *Bang!* en entrant "`Raté`"

## Prison (*Jail*)
<img width="100px" alt="Jail" src="../web/images/cards/jail_4H.png" />

**Détails :** Le joueur doit entrer *le nom du joueur* à mettre en prison. La carte est alors placée en jeu devant le joueur ciblé. Au début du tour d'un joueur qui a une carte *Prison* devant lui, un tirage aléatoire ("dégainer") est fait automatiquement. Dans tous les cas, la carte *Prison* est défaussée après le tirage. Si la carte tirée est un cœur, le joueur joue son tour normalement, sinon il passe son tour

**Remarques :**
- Un joueur qui passe son tour ne pioche pas de cartes
- Un joueur qui passe son tour n'a pas à défausser de cartes s'il en a plus en main que de points de vie restants
- La carte *Prison* devant un joueur peut être gagnée en main avec un *Braquage!* ou défaussée avec un *Coup de foudre*
- Si un joueur a une *Prison* et une *Dynamite* devant lui, c'est la *Dynamite* qui est résolue en premier, puis la *Prison* si le joueur est toujours en vie
- Si un joueur est tué alors qu'il est en *Prison*, la carte est défaussée (sauf si *Sam le Vautour* est en jeu, auquel cas il récupère la carte *Prison* en main)


## Raté! (*Missed!*)
<img width="100px" alt="Missed!" src="../web/images/cards/missed_2S.png" />

**Détails :** Lorsqu'un joueur est la cible d'un effet *Bang!* (carte *Bang!* ou *Gatling*), il peut entrer "`Missed!`" pour défausser une carte *Raté!* de sa main ou passer et subir un point de dégâts.

**Remarques :**
- *Calamity Janet* peut entrer "`Bang!`" pour défausser une carte *Bang!* de sa main à la place d'un *Raté!* et éviter ainsi les dégâts d'un effet *Bang!*
- *Calamity Janet* peut également jouer une carte *Raté!* de sa main au cours de son tour, ce qui doit exécuter la même action que lorsqu'un joueur joue une carte *Bang!*


## Mustang
<img width="100px" alt="Mustang" src="../web/images/cards/mustang_8H.png" />

**Détails :** Aucune intervention de la part du joueur


## Braquage! (*Panic!*)
<img width="100px" alt="Panic!" src="../web/images/cards/panic_8D.png" />

**Détails :** Le joueur entre d'abord *le nom du joueur* ciblé, puis il entre *le nom de la carte* à prendre parmi celles posées devant la cible, ou il passe pour prendre une carte aléatoirement de la main de la cible.

**Remarques :**
- La carte gagnée est placée dans la main du joueur qui a joué le *Braquage!*
- On ne peut cibler qu'un joueur à distance 1 (ou moins) les modifications de distance (*Mustang*, *Lunette*, *Paul Regret* et *Rose Doolan*) sont pris en compte mais pas la portée de l'arme
- Il est possible de prendre l'arme d'un joueur
- Les cartes *Prison* et *Dynamite* posées devant un joueur peuvent également être prises en main
- Si aucun joueur n'est à distance 1 (ou moins), la carte ne peut pas être jouée
- Il est possible de choisir de prendre une carte aléatoirement dans la main d'un joueur qui n'a aucune carte en main, sans effet


## Remington
<img width="100px" alt="Remington" src="../web/images/cards/remington_KC.png" />

**Détails :** Aucune intervention de la part du joueur


## Carabine (*Rev. Carabine*)
<img width="100px" alt="Rev. Carabine" src="../web/images/cards/revcarabine_AC.png" />

**Détails :** Aucune intervention de la part du joueur


## Saloon
<img width="100px" alt="Saloon" src="../web/images/cards/saloon_5H.png" />

**Détails :** Aucune intervention de la part du joueur

**Remarques :**
- Le *Saloon* a toujours un effet, même s'il ne reste que deux joueurs vivants en jeu
- On peut jouer un *Saloon* même si tous les joueurs ont tous leurs points de vie, sans effet


## Schofield
<img width="100px" alt="Schofield" src="../web/images/cards/schofield_JC.png" />

**Détails :** Aucune intervention de la part du joueur


## Lunette (*Scope*)
<img width="100px" alt="Scope" src="../web/images/cards/scope_AS.png" />

**Détails :** Aucune intervention de la part du joueur

## Convoi (*Stagecoach*)
<img width="100px" alt="Stagecoach" src="../web/images/cards/stagecoach_9S.png" />

**Détails :** Aucune intervention de la part du joueur

## Volcanic
<img width="100px" alt="Volcanic" src="../web/images/cards/volcanic_10C.png" />

**Détails :** Aucune intervention de la part du joueur

## Diligence (*Wells Fargo*)
<img width="100px" alt="Wells Fargo" src="../web/images/cards/wellsfargo_3H.png" />

**Détails :** Aucune intervention de la part du joueur

## Winchester
<img width="100px" alt="Winchester" src="../web/images/cards/winchester_8S.png" />

**Détails :** Aucune intervention de la part du joueur
