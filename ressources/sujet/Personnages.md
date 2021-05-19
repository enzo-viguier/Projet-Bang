# Description des personnages

## Bart Cassidy
<img width="100px" alt="Bart Cassidy" src="../web/images/characters/bartcassidy.png" />

**Description :** Chaque fois qu’il perd un point de vie, il pioche immédiatement une carte.

**Détails :** Aucune intervention de la part du joueur (la carte est piochée automatiquement)


## Black Jack
<img width="100px" alt="Black Jack" src="../web/images/characters/blackjack.png" />

**Description :** Durant la phase 1 de son tour, il doit montrer la seconde carte qu’il a piochée. Si c’est un Cœur ou un Carreau (comme dans le cas d’un «dégaine !»), il tire une carte supplémentaire (sans la révéler).

**Détails :** Aucune intervention de la part du joueur.

**Remarques :**
- Dans ce projet, étant donné que toutes les informations sont affichées, il n'est pas nécessaire de faire quoi que ce soit pour "révéler" la 2e carte piochée.


## Calamity Janet
<img width="100px" alt="Calamity Janet" src="../web/images/characters/calamityjanet.png" />

**Description :** Elle peut utiliser les cartes *Bang!* comme des cartes *Raté!* et vice-versa. Si elle joue un *Raté!* à la place d’un *Bang!*, elle ne peut pas jouer d’autre carte *Bang!* durant son tour (à moins d’avoir un *Volcanic* en jeu)

**Détails :** Pendant son tour, si elle a une carte *Raté!* en main, le joueur peut entrer "`Raté!`" ce qui doit faire jouer la carte *Raté!* comme si c'était un *Bang!*. De manière analogue, lorsqu'elle a la possibilité de jouer une carte *Bang!* ou *Raté!* en réponse à une action (*Duel*, *Indiens!*, *Bang!*, etc.) elle peut entrer le *nom de la carte* qu'elle a en main et la joueur comme si c'était l'autre. 


## El Gringo
<img width="100px" alt="El Gringo" src="../web/images/characters/elgringo.png" />

**Description :** Chaque fois qu’il perd un point de vie à cause d’une carte jouée par un autre joueur, il tire une carte au hasard dans la main de ce dernier (une carte par point de vie). Si ce joueur n’a plus de cartes, dommage, il ne peut pas lui en prendre ! N’oubliez pas que les points vie perdus à cause de la dynamite ne sont pas considérés comme étant causés par un joueur.

**Détails :** Aucune interaction de la part du joueur (les cartes sont piochées automatiquement).

**Remarques :**
- Utilisez l'argument `attacker` de la méthode `Player.decrementHealth(int, Player)` pour savoir qui a infligé les dégâts


## Jesse Jones
<img width="100px" alt="Jesse Jones" src="../web/images/characters/jessejones.png" />

**Description :** Durant la phase 1 de son tour, il peut choisir soit de piocher la première carte de la pioche, soit de prendre 1 carte au hasard dans la main d’un autre joueur. Il pioche ensuite sa deuxième carte dans la pioche.

**Détails :** Au début de son tour, le joueur peut entrer *le nom du joueur* dans la main duquel il veut piocher une carte, ou passer pour prendre sa première carte dans la pioche. La seconde carte est automatiquement piochée dans la pioche.

**Remarques:**
- N'importe quel adversaire ayant des cartes en main peut être choisi pour piocher la première carte (indépendamment des distances)
- Si aucun adversaire n'a de cartes en main, les deux cartes sont automatiquement piochées dans la pioche


## Jourdonnais
<img width="100px" alt="Jourdonnais" src="../web/images/characters/jourdonnais.png" />

**Description :** On considère qu’il a une *Planque* en jeu à tout moment. Il peut « dégainer ! » quand il est la cible d’un *Bang!*, et s’il tire un cœur, le tir l’a raté. S’il a une autre vraie carte *Planque* en jeu, il peut l’utiliser également, ce qui lui donne deux chances d’annuler un *Bang!* avant d’avoir à jouer un *Raté!*

**Détails :** Aucune intervention de la part du joueur.


## Kit Carlson
<img width="100px" alt="Kit Carlson" src="../web/images/characters/kitcarlson.png" />

**Description :** Durant la phase 1 de son tour, il regarde les trois premières cartes de la pioche, en choisit 2 qu’il garde et repose la troisième sur la pioche, face cachée.

**Détails :** Au début du tour, le joueur doit entrer *le nom de la carte* qu'il veut replacer sur la pioche (c'est plus simple que de lui faire choisir deux fois les cartes qu'il veut garder). La carte est replacée sur le dessus de la pioche et les deux autres cartes tirées sont placées dans sa main.

**Remarques :**
- Vous pouvez passer `true` comme valeur à l'argument `showButtons` de la méthode `Player.chooseCard()` pour afficher à l'écran les trois cartes qui ont été piochées (dans l'interface graphique et en mode console)


## Lucky Duke
<img width="100px" alt="Lucky Duke" src="../web/images/characters/luckyduke.png" />

**Description :** Chaque fois qu’il doit « dégainer », il retourne les deux premières cartes de la pioche et choisit le résultat qu’il préfère. Il défausse ensuite les deux cartes.

**Détails :** À chaque tirage aléatoire ("dégainer"), deux cartes sont retournées du dessus de la pioche puis le joueur doit entrer *la valeur de poker de la carte* qu'il choisit. Par exemple, si les cartes retournées sont un *Duel* dont la valeur de poker est 8 de pique, et un *Saloon* dont la valeur de poker est une Dame de cœur, le joueur peut entrer "`Q♥`" pour choisir la Dame de cœur (ce n'est pas le nom de la carte qui est entré !).

**Remarques :** 
- Vous pouvez utiliser la méthode `Card.getPokerString()` pour obtenir la chaîne de caractères représentant la valeur de poker d'une carte (par exemple "`Q♥`")
- Utilisez la méthode `Player.choose(String, List<String>, boolean, boolean)` pour demander au joueur de choisir la carte. La liste `choices` doit contenir les chaines de caractères représentant les valeurs de poker des cartes (p.ex. "`8♠`", "`Q♥`"). Passez la valeur `true` au paramètre `showButtons` pour que les choix possibles soient affichés dans l'interface graphique et en mode console


## Paul Regret
<img width="100px" alt="Paul Regret" src="../web/images/characters/paulregret.png" />

**Description :** On considère qu’il a un *Mustang* en jeu à tout moment : tous les autres joueurs doivent ajouter 1 à la distance qui les sépare de lui. S’il a un autre *Mustang* réel en jeu, il peut utiliser les deux, ce qui augmente la distance de 2 en tout. 

**Détails :** Aucune intervention de la part du joueur.


## Pedro Ramirez
<img width="100px" alt="Pedro Ramirez" src="../web/images/characters/pedroramirez.png" />

**Description :** Durant la phase 1 de son tour, il peut choisir de piocher la première carte de la défausse au lieu de la prendre dans la pioche. Il pioche sa seconde carte normalement, dans la pioche.

**Détails :** Au début de son tour et si la défausse n'est pas vide le joueur peut entrer *le nom de la carte* qui se trouve sur le dessus de la défausse pour la piocher en main, ou passer pour prendre sa première carte dans la pioche. La seconde carte est automatiquement prise dans la pioche.

**Remarques :**
- Si la défausse est vide, le joueur pioche automatiquement ses deux cartes dans la pioche


## Rose Doolan
<img width="100px" alt="Rose Doolan" src="../web/images/characters/rosedoolan.png" />

**Description :** On considère qu’elle a une Lunette en jeu à tout moment : la distance de tous les autres joueurs est réduite de 1 pour elle. Si elle a une autre Lunette réelle en jeu, elle peut utiliser les deux, ce qui réduit la distance de tous les autres joueurs de 2 en tout.

**Détails :** Aucune intervention de la part du joueur.


## Slab le flingueur (Slab the Killer)
<img width="100px" alt="Slab le flingueur" src="../web/images/characters/slabthekiller.png" />

**Description :** quand il joue une carte *Bang!* contre un joueur, celui-ci doit dépenser 2 cartes *Raté!* au lieu d’une pour l’annuler. L’effet de la *Planque* ne compte que pour une carte *Raté!*

**Détails :** Lorsqu'un joueur est la cible d'une carte *Bang!* jouée par *Slab le flingueur*, on demande à celui-ci d'entrer `Missed!` ou de passer jusqu'à ce qu'il ait défaussé deux cartes *Raté!*. S'il passe avant d'avoir défaussé deux ratés, il subit un point de dégâts (même s'il a déjà défaussé un *Raté!*).

**Remarques :**
- Si le joueur a une protection (*Planque* ou *Jourdonnais*), le tirage est fait automatiquement. Si la protection fonctionne (cœur) alors le joueur doit encore défausser un *Raté!* pour éviter les dégâts
- Si le joueur a deux protections (*Planque* **et** *Jourdonnais*) et que les deux réussissent, alors le joueur n'a pas à défausser de *Raté!* 


## Suzy Lafayette
<img width="100px" alt="Suzy Lafayette" src="../web/images/characters/suzylafayette.png" />

**Description :** Dès qu’elle n’a plus aucune carte en main, elle prend une carte dans la pioche.

**Détails :** Aucune intervention de la part du joueur (la carte est piochée automatiquement lorsque le joueur n'a plus de carte en main).


## Sam le vautour (Vulture Sam)
<img width="100px" alt="Sam le vautour" src="../web/images/characters/vulturesam.png" />

**Description :** Dès qu’un personnage est éliminé de la partie, Sam prend toutes les cartes que ce joueur avait en main et en jeu, et il les ajoute à sa propre main.

**Détails :** Aucune intervention de la part du joueur.


## Willy the Kid
<img width="100px" alt="Willy the Kid" src="../web/images/characters/willythekid.png" />

**Description :** Il peut jouer autant de cartes *Bang!* qu’il le désire pendant son tour.

**Détails :** Aucune intervention de la part du joueur.
