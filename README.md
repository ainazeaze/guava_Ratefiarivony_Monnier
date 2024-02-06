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

## 1.1 Utilité du projet 
### - Readme dans le projet  
Le readme est pertinent, il contient beaucoup de liens vers des aides externes, contient aussi le guide d'installation et enfin les avertissements importants sur l'utilisation de Guava.
Cela fait environ 1 mois que le Readme n'a pas été modifié mais la plupart des modifications effectuées durant ce mois ont été des modifications sur des tests ou sur des suppresion warnings et donc il n'y a pas eu besoin de changer le README.

### - Readme dans le projet  








