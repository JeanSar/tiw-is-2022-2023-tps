# Compte Rendu TP3
## Communication par messages asynchrone avec RabbitMQ

### Q0.1. Indiquer les noms, prénoms et numéros d'étudiants du binôme.
- Jean Saury p1805563
- Laetitia Castaldo p1710690

## 1. Prise en main du code fourni
### Q1.1. Lister les routes et les composants qui sont déjà définis dans chain-manager.


### Q1.2. Sur quel port l'application chain-manager écoute-elle ? Où se port est-il configuré ?


### Q1.3. Lister les routes et les composants qui sont déjà définis dans machine. Expliquer quelle morceau de configuration empêche l'application de démarrer un serveur web alors que la dépendance sur spring-boot-stater-web est bien présente dans le pom.xml.


### Q1.4. Expliquer pourquoi machine affiche Reçoit les messages sur la queue 'queue-machine1' au démarrage. Ce message reflète-il la réalité en l'état ?


### Q1.5. chain-manager est configurée pour recevoir des messages. Sur quelle queue va-t-elle les chercher ?


### Q1.6. L'application chain-manager a-t-elle traité le message ? Quelle(s) méthode(s) ont-elles été appelée(s) ? Ajoutez au besoin des logs pour vérifier votre hypothèse.


## 2. Clients REST


### Q2.1. Quelles modifications avez-vous apporté à chain-manager ? Copier/coller le code de getMachines.

### Q2.2. Y a-t-il besoin d'informations additionnelles pour réaliser ces changements ? Si oui, lesquelles ?


### Q2.3. Quels problèmes risquent de se poser avec la gestion des informations des machines du catalogue telle qu'elle est proposée ?

### Q2.4. Quels changements avez-vous apporté aux applications et enquoi ces changements résolvent-ils le problème précédent ?


## 3. Réception de message par les machines

### Q3.1. Quelles erreurs peuvent se produire lors de la gestion du JSON ?Quels problèmes se posent si on souhaite gérer correctement ces erreurs ?


### Q3.2 Copier/coller le code de votre @RabbitListener

## 4. Envoi de message par chain-manager et machine

### Q4.1 Copier/coller le code de la méthode envoieOptionsVoiture

### Q4.2 Seul le premier appel déclenche la production de message(s) dans RabbitMQ. Pourquoi ?

### Q4.3. Quelles sont les informations transmises lors de la confirmation de reconfiguration ? Avez-vous du ajouter des données dans la demande de reconfiguration ? Si oui, lesquelles ?


## 5. Synchronisation et démarrage de la configuration suivante

### Q5.1. Quel champ de Voiture dans chain-manager est particulièrement utile ici ? A quel moment ce champ doit-il être réellement initialisé ?

### Q5.2. Avez-vous du ajouter des informations dans certains messages ? Si oui, lesquelles ?

### Q5.3 Normalement, la voiture ne passe pas au statut TERMINE. Pourquoi ? Élaborer une stratégie qui permettrait de mitiger (à défaut de faire disparaitre) le problème.

