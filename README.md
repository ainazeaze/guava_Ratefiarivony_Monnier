# 1. Présentation globale du projet
## 1.1 Utilité du projet 
### - Utilité et fonctionnalité
Google Guava est une bibliothèque open-source développée par Google pour Java. Elle étend les fonctionnalités de la bibliothèque standard Java, fournissant des utilitaires pour les collections, la programmation fonctionnelle, la manipulation de chaînes, la gestion des événements, la concurrence, et d'autres fonctionnalités. Guava vise à rendre le développement Java plus efficace en offrant des classes et des utilitaires prêts à l'emploi pour des tâches courantes.  

### - Comment l'utiliser?  
Pour l'utiliser avec Maven, il faut ajouter la dépendance Guava comme suit :  
```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>33.0.0-jre</version>
  <!-- or, for Android: -->
  <version>33.0.0-android</version>
</dependency>
```  
Pour l'utiliser avec Gradle, il faut ajouter la dépendance comme suit: 
```gradle
dependencies {
  // Pick one:

  // 1. Use Guava in your implementation only:
  implementation("com.google.guava:guava:33.0.0-jre")

  // 2. Use Guava types in your public API:
  api("com.google.guava:guava:33.0.0-jre")

  // 3. Android - Use Guava in your implementation only:
  implementation("com.google.guava:guava:33.0.0-android")

  // 4. Android - Use Guava types in your public API:
  api("com.google.guava:guava:33.0.0-android")
}
```  

## 1.2 Description du projet
### - Readme dans le projet  
Le readme est pertinent, il contient beaucoup de liens vers des aides externes, contient aussi le guide d'installation et enfin les avertissements importants sur l'utilisation de Guava.
Cela fait environ 1 mois que le Readme n'a pas été modifié mais la plupart des modifications effectuées durant ce mois ont été des modifications sur des tests ou sur des suppresion warnings et donc il n'y a pas eu besoin de changer le README.

### - Documentation du projet  
Ce projet est bien fourni avec une documentation. Comme il s'agit d'une librairie, la documentation montre les instructions à suivre pour ajouter la dépendance.

### - Complétude de la documentation  
Les informations paraissent suffisante pour se servir de la librairie. Cependant la documentation a été mis à jour pour la dernière fois en 2016, donc il se peut que  certaines informations sont absentes  

# 2. Historique du logiciel  
## 2.1 Analyse du git
### - Composition de l'équipe    
Il y a 299 contributeurs. Chaque membre ne contribue de manière équilibrée. Ce n'est pas non plus équilibré dans le temps car certain contributeurs ont contribué en 2017 et ne sont plus revenu on revienne des années plus tard.  

### - Acitivité du projet  
Le projet est toujours en activité. Elle est assez répartie sur le temps.  
Voici un diagramme illustrant l'activité en fonction du temps le nombre de commits par jour :  
![UML](image/image.png "Text to show on mouseover")  

### - Utilisation des branches  
Il ya 28 branches au total, dont seulement 5 sont actives en comptant master.  

### - Utilisation des pull requests  
Le mécanisme des pull requests est utilisé et il y en a 93 en attentes.  

# 3. Architecture logicielle  
## 3.1 Utilisation de bibliothèque extérieures  
### - Nombre de bibliothèques extérieures utilisées  
Nombre de bibliothèques extérieures : 7 maven dependencies 

## - Utilisation des bibliothèques extérieures  
Elles sont toutes utilisées 

## - 


## 3.2 Organisation en paquetages  
### - Nombre de paquetages  
Il y a 15 paquetages.  

### - Liens entre les paquetages  
Par le métrique Efferent Coupling, on a trouvé que le paquetage **com.google.common.collect** importe un total de 222 autre paquetages. Voici un graphe illustrant cela :  

![UML](image/Efferent.png "Text to show on mouseover")  

Par le métrique Afferent Coupling, on a trouvé que le paquetage **com.google.common.annotations** a été importé à un total de 1105 fois dans d'autre paquetages. Voici un graphe illustrant cela :  

![UML](image/Afferent.png "Text to show on mouseover")  














