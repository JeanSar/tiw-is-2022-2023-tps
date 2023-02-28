# Compte Rendu TP3
## Communication par messages asynchrone avec RabbitMQ

http://localhost:8081/swagger-ui/index.html

### Q0.1. Indiquer les noms, prénoms et numéros d'étudiants du binôme.
- Jean Saury p1805563
- Laetitia Castaldo p1710690

## 1. Prise en main du code fourni
### Q1.1. Lister les routes et les composants qui sont déjà définis dans chain-manager.

Dans Chain-Manager il y a deux routes : 
- POST : /voiture/ -> ajouteVoiture()
- GET : /voiture/ -> getAllVoitures()

Au niveau des composants on retrouve : 
  - ConfigurationConfirmationReceiver
  - VoitureController
  - Statut
  - StatutInconnuException
  - voiture
  - VoitureRepository
  - MachineService
  - VoitureService
  - MachineDTO
  - VoitureDTO


### Q1.2. Sur quel port l'application chain-manager écoute-elle ? Où se port est-il configuré ?

Sur le port 8081, on peut le voir sur le application.properties

### Q1.3. Lister les routes et les composants qui sont déjà définis dans machine. Expliquer quelle morceau de configuration empêche l'application de démarrer un serveur web alors que la dépendance sur spring-boot-stater-web est bien présente dans le pom.xml.

Il n'y a pas de route sur machine, son unique composant est le suivant :
- ConfigurationService

Dans application.properties du module machine, on trouve la ligne suivante : spring.main.web-application-type=none

Le statut NONE signifie que l'application ne doit pas s'exécuter en tant qu'application web et ne doit pas démarrer un serveur web intégré.

### Q1.4. Expliquer pourquoi machine affiche Reçoit les messages sur la queue 'queue-machine1' au démarrage. Ce message reflète-il la réalité en l'état ?

Ce message provient du runner de machine, ce n'est pas encore le cas -> TODO : expliquer 

### Q1.5. chain-manager est configurée pour recevoir des messages. Sur quelle queue va-t-elle les chercher ?
Elle recevra les messages sur la queue 'chainmanager'

### Q1.6. L'application chain-manager a-t-elle traité le message ? Quelle(s) méthode(s) ont-elles été appelée(s) ? Ajoutez au besoin des logs pour vérifier votre hypothèse.

Oui le message est bien traité.
C'est la méthode ConfiguratoinConfirmationReceiver.receive() qui est appelée.
Le seul endroit où il y a un RabbitListener est dans cette méthode.

## 2. Clients REST


### Q2.1. Quelles modifications avez-vous apporté à chain-manager ? Copier/coller le code de getMachines.
Nous avons ajouté une classe de configuration RestTemplateClient définissant le bean restTemplate.
Nous avons également ajouté une propriété autowired restTemplate dans MachineServices.
```java
public Collection<MachineDTO> getMachines() {
    try {
        log.info(String.format("GET %s", ROOT_URI));
        ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity(ROOT_URI, MachineDTO[].class);
        MachineDTO[] machines = response.getBody();
        if(machines == null) {
            return null;
        } else {
            return Arrays.asList(machines);
        }
    } catch (RestClientException e) {
        log.error(e.getMessage());
        return null;
    }
}
```

### Q2.2. Y a-t-il besoin d'informations additionnelles pour réaliser ces changements ? Si oui, lesquelles ?

Oui, il est nécéssaire de rajouter le champ 'queue' dans le modèle de Machine de catalogue. 

### Q2.3. Quels problèmes risquent de se poser avec la gestion des informations des machines du catalogue telle qu'elle est proposée ?

Les id des machines sont autoincrémentés au niveau du catalogue. 

### Q2.4. Quels changements avez-vous apporté aux applications et enquoi ces changements résolvent-ils le problème précédent ?

On explique comment on gère les ids.

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

