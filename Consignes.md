**Date de rendu : 9 mai 2021 à 23h00**  

### Consignes pour le fork
Les étapes à suivre par chaque équipe pour organiser correctement votre projet :

  0. Constituez une équipe de 3 ou 4 étudiants (issus du même groupe de TD, et de préférence du même sous-groupe)
  1. __Un seul membre de l'équipe__ fera le fork avec le lien GitHub Classroom (càd acceptera l'affectation du projet sur GitHub Classroom) : https://classroom.github.com/g/XfAlmVYQ
  2. Au moment de l'acceptation de l'invitation, ce membre d'équipe choisira un nom d'équipe. Ce nom sera constitué des noms de famille de chaque membre accolés par ordre alphabétique. Par exemple, si les noms sont Dupont, Robert, Martin et Durand, alors l'équipe s'appellera : `DupontDurantMartinRobert`.
  3. Ensuite, une fois le projet forké avec Classroom, l'étudiant invitera les autres membres de l'équipe : *Settings &longrightarrow; Manage Access &longrightarrow; Invite teams or people*. À vous de choisir si vous voulez qu'un d'entre vous (ou tous) ait les droits d'administration. À partir de ce moment vous pourrez travailler de façon collaborative sur votre dépôt.
    
### Consignes générales
* Pour que le projet soit fini, vous devez implémenter correctement l'ensemble de méthodes levant une exception avec l'instruction `throw new RuntimeException("Méthode non implémentée !")`.
* En utilisant les méthodes qui vous sont données, vous pouvez être amenés à les modifier légèrement. Ces modifications ne devraient pas affecter leur fonctionnement général.
* N'hésitez pas à _ajouter_ des fonctions utilitaires qui vous paraissent nécessaires. 
* Vous respecterez les bonnes pratiques en programmation objet vues en cours.  
* Le respect des conventions de nommage du langage Java est **impératif**.
* Votre base de code **doit être en permanence testée** et donc toujours être dans un état sain (le code compile et les tests passent). **Un programme qui ne compile pas ne doit en aucun cas être soumis avec `git commit`!**
* Comment tester votre programme ? Voici quelques consignes :

    1. En écrivant vos propres tests unitaires.  
    2. En exécutant les tests unitaires qui vous ont été fournis dans le repertoire `src/test/java` :
        * La plupart de ces tests sont annotés `@Disabled` et donc pour l'instant ils sont ignorés.
        * Au fur et à mesure de l'avancement de votre projet vous activerez chaque test (en supprimant l'annotation `@Disabled`) et vérifierez que ce test passe.
        * **Attention** : n'essayez pas de passer tous les tests en même temps (principe _BabySteps_)
        * Ne faites pas de `git add/commit` tant que des tests non-annotés `@Disabled` ne passent pas.
        * **Remarque** : soyez vigilants même si votre programme passe tous les tests, car en règle générale un programme ne peut **jamais** être suffisamment testé...
    
* Certaines précisions ou consignes pourront être ajoutées ultérieurement et vous en serez informés.
* Surveillez l'activité sur [le forum](https://piazza.com/class/kjifrxy1n0i3xa), les nouvelles informations y seront mentionnées. N'hésitez pas à y poser des questions, surtout si les réponses pourraient intéresser les autres équipes.

### Conseils concernant la gestion de version
* Chaque commit devrait être accompagné d'un message permettant de comprendre l'objet de la modification.
* Vos _commits_ doivent être les plus petits possibles. Un commit qui fait 10 modifications de code sans lien entre elles, devra être découpé en 10 _commits_.
* On vous conseille d'utiliser des _branches Git_ différentes lors du développement d'une fonctionnalité importante. Le nom de la branche doit être au maximum porteur de sens. Une fois que vous pensez que le code de la fonctionnalité est fini (tous les tests associés à celle-ci passent), vous fusionnerez le code de sa branche avec la branche `master`.
