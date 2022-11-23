# SonarQube

* Clean Code, code quality
* Code Security
* Technical debt
* Static Code Analysis rules ezrei
* 30+ különböző nyelv
* Project overview
* History
* CI/CD integráció

# Fejlesztési folyamat

![Folyamat](images/dev-cycle.png)

# Új verziók

* [Whats new](https://www.sonarqube.org/whats-new/)
* Viszonylag gyakran jönnek ki új verziók

# Különböző kiadások

* [Developer Edition](https://www.sonarqube.org/developer-edition/)
    * Branch analysis
    * Pull/merge request analysis
    * Pull/merge request decoration (GitHub Enterprise/SaaS, GitLab self-managed/SaaS, Azure DevOps Server, Bitbucket)
    * Applications: több projekt összegyűjtése (meta-project, aggregation)
    * Több nyelv (C, C++, Obj-C, PL/SQL, ABAP, TSQL & Swift)
* [Enterprise Edition](https://www.sonarqube.org/enterprise-edition/)
    * Portfolio (management)
    * PDF reporting
    * [OWASP/CWE Security Reports](https://www.sonarqube.org/features/security/owasp/)
    * Faster analysis
    * Több nyelv (Apex, COBOL, PL/I, RPG & VB6)
    * Project move 
        * Export/import
        * Projekt költöztetéskor és kell a history
        * Célnak kell lennie EE-nek
    * Audit Log
* [Data Center Edition]
    * High Availability and Scalability
    * Component redundancy
    * Data resiliency
    * Horizontal scalability

# Árazás

* Developer Edition
    * $150/yr
    * maximum of 100,000 LOC
    * bővíthető: $65K/yr for a maximum of 20M LOC
* Enterprise Edition
    * $20K/yr for a maximum of 1M LOC
    * bővíthető: $240K/yr for a maximum of 100M LOC
* Data Center Edition

# SonarQube architektúra

* Embedded Elasticsearch
* Embedded H2
* Java folyamatok
    * Compute Engine
    * Elasticsearch
    * Web (including embedded web server)


# SonarQube DE, EE architektúra

* Postgres, Oracle, MS SQL Server támogatás
* Csak a DCE skálázható, egy adatbázishoz nem kapcsolódhat több példány

# Clusterezés

* Two application nodes
* Three search nodes

# Infrastruktúra

* [Gitea](https://gitea.io/en-us/)
* Jenkins
* SonarQube
* Mailhog
* Docker konténerekben, hálózatban

# Infrastruktúra elindítása

```
cd infra
docker compose up -d
docker logs -f 
```

* Docker Compose biztosítja a hálóztatot

http://localhost:3000/
http://localhost:8080/
http://localhost:9000/

* Sonar bejelentkezés: `admin/admin`, jelszó váltása kötelező
* Nem megfelelő jelszó esetén értelmezhetetlen hibaüzenet, értelmezhető a logban

```
docker compose logs -f
```

# Futtatás Dockerrel

* [Docker image](https://hub.docker.com/_/sonarqube)
* Volumes
* `/opt/sonarqube/data`: data files, such as the embedded H2 database and Elasticsearch indexes
* `/opt/sonarqube/logs`: contains SonarQube logs about access, web process, CE process, Elasticsearch logs
* `/opt/sonarqube/extensions`: for 3rd party plugins (JDBC driver is)
* Image-en belül a `/opt/sonarqube` könyvtárban

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
```

* [Környezeti változók](https://docs.sonarqube.org/latest/setup/environment-variables/)

# Git

* Gitea Initial Configuration
* Register
* Settings/Appearance/Language
* Create repository


```
init
add
commit
remote
push
```

Autentikáció: Hitelesítőadat-kezelő


# SonarQube első elemzés

* Create a project manually
* Generate token (My Account/Security/Tokens)
* `java -version`
* `JAVA_HOME` környezeti változó beállítása
* [SonarScanner for Maven](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/) (javasolt)

```
mvnw clean verify sonar:sonar -Dsonar.projectKey=employees-app -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_06d366199beb6696660bcac0d3fc09dda79bf8b9
```

* Java: bytecode analysis is van, ezért fordításnak le kell futnia

# Aszinkron analysis

* Aszinkron analysis
    * In progress ikon a felületen
* Administration/Projects/Background Tasks
    * Show Scanner Context
* Hiba esetén:
    * Profile/Notifications - _Background tasks in failure on my administered projects_ Email

# Konfiguráció

* Property-k: [Analysis parameters](https://docs.sonarqube.org/latest/analysis/analysis-parameters/)
    * Maven specifikus: `sonar.skip` multimodule projekt esetén modul átugrására
* Global properties: összes projektre. Defined in the UI in Administration > Configuration > General Settings
* Project properties: adott projektre. Project Settings > General Settings
* Project analysis parameters: konfig.

```xml
<properties>
  <sonar.buildString> [...] </sonar.buildString>
</properties>
```

* Analysis / Command line parameters: parancssori, `-D` kapcsolóval.

![Settings hierarchy](images/settings-hierarchy.png)

# Projekt leírók

* Átvesz projekt leírókat a Maventől, pl. description, URL, CI, issue tracker, SCM URL
* Property-kkel felül lehet írni


# Rules

* Types:
    * Code Smell (Maintainability domain)
    * Bug (Reliability domain)
    * Vulnerability (Security domain)
    * Security Hotspot (Security review domain)
* [Beépített tagekkel](https://docs.sonarqube.org/latest/user-guide/built-in-rule-tags/) rendelkeznek
    * Pl.
        * CWE Top 25: SonarQube is a CWE compatible product since 2015.
        * PCI DSS (versions 4.0 and 3.2.1)
        * OWASP Top 10
        * OWASP ASVS (version 4.0)
        * https://en.wikipedia.org/wiki/CERT_Coding_Standards
    * Lehet hozzá taget adni
* Ki lehet egészíteni a leírást: Extend Description

# Default severity

![Severity](images/severity.jpg)

# Quality profiles

* Java / Sonar way

# Reliability Rating (reliability_rating)

* A = 0 Bugs
* B = at least 1 Minor Bug
* C = at least 1 Major Bug
* D = at least 1 Critical Bug
* E = at least 1 Blocker Bug

# Reliability remediation effort

* Effort to fix all bugs

# Security Rating (security_rating)

* A = 0 Vulnerabilities
* B = at least 1 Minor Vulnerability
* C = at least 1 Major Vulnerability
* D = at least 1 Critical Vulnerability
* E = at least 1 Blocker Vulnerability

# Security Review

* Security Hotspot: Manuális review szükséges, hogy vulnerability-e vagy sem

# Technical Debt

* Mennyi idő kell, ha napokban van, az 8 órás napokkal számol
* Ruleokhoz tartozó érték
* Remediation Cost	The estimated time required to fix Vulnerability and Reliability Issues
* Technical Debt Ratio: Remediation cost / Development cost
    * Remediation cost / (Cost to develop 1 line of code * Number of lines of code)
* Cost to develop one line of code (LOC)
    * Administration/Global Settings/Technical Dept
    * `sonar.technicalDebt.developmentCost`
    * Default 30 perc

# Maintainability Rating

* Régebben: SQALE 
* A=0-0.05, B=0.06-0.1, C=0.11-0.20, D=0.21-0.5, E=0.51-1
* A 5%-a a beletett időnek kell a hibajavításhoz

# Issues

* Types:
    * Bugs
    * Code Smells
    * Vulnerabilities

# Szűrés

* Pl. creation date grafikon alapján

# Felhasználókezelés

* Összerendelés felhasználónév, e-mail cím alapján (Login and email correlations)
    * Fel kell venni a commitot végző felhasználót
* Automatikusan ahhoz rendeli, aki commitolta (Blame, Automatic Issue Assignment)
* My Issues szűrés

# HTML/CSS fájlok

```xml
<sonar.sources>src/main/java,src/main/resources/static,src/main/resources/templates</sonar.sources>
```

* General settings/Languages

# JS fájlok

* JavaScript/TypeScript
* React JSX, Vue.js, Angular


```
choco install nodejs
```

# Tesztlefedettség

* [jacoco-maven-plugin](https://www.eclemma.org/jacoco/trunk/doc/maven.html)
* XML formátumú riportot kell gyártani

# Jacoco konfiguráció

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.7</version>
    <executions>
        <execution>
            <id>jacoco-initialize</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
                <goal>report</goal>
            </goals>
            <configuration>
                <formats>
                <format>XML</format>
                </formats>
            </configuration>
        </execution>
    </executions>
</plugin>
```

# Profillal

```xml
 <profile>
  <id>coverage</id>
  <build>
   <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
     <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.7</version>
      <executions>
        <execution>
          <id>prepare-agent</id>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <goals>
            <goal>report</goal>
          </goals>
          <configuration>
            <formats>
              <format>XML</format>
            </formats>
          </configuration>
        </execution>
      </executions>
    </plugin>
    ...
   </plugins>
  </build>
</profile>
```

# XML riport fájlok helye

* Jacoco, report files path

# Line coverage (line_coverage)

* Line coverage = LC / EL
* LC = covered lines
* EL = total number of executable lines (lines_to_cover)

# Condition coverage (branch_coverage)

* Condition coverage = (CT + CF) / (2*B)
* CT = conditions that have been evaluated to 'true' at least once
* CF = conditions that have been evaluated to 'false' at least once
* B = total number of conditions

# Coverage

* Kombinált mérőszám
* Coverage = (CT + CF + LC)/(2*B + EL)
* CT = conditions that have been evaluated to 'true' at least once
* CF = conditions that have been evaluated to 'false' at least once
* LC = covered lines = linestocover - uncovered_lines
* B = total number of conditions
* EL = total number of executable lines (lines_to_cover)

# Duplications

* Javaban 10 kifejezés, függetlenül a tokenek és sorok számától, whitespace-ektől

# Measures

* [Metric definitions](https://docs.sonarqube.org/latest/user-guide/metric-definitions/)

# Complexity (complexity)

* Complexity: lehetséges útvonalak száma
* Kulcsszavak, melyek a komplexitást növelik: `if`, `for`, `while`, `case`, `catch`, `throw`, `&&`, `||`, `?`
* Cognitive Complexity (cognitive_complexity): Sonar által fejlesztett [mathematical models](https://www.sonarsource.com/resources/white-papers/cognitive-complexity/)

# Grafikus ábrázolás

* Két dimenzió, szín, méret
* Alapértelmezett: tesztlefedettség + Technical Debt, szín: legrosszabb rating, méret: lines of code
* Nagyítható (egér görgővel)

# Quality Profile

* Jó defaultot ad
* Érdemes minden projektre ugyanazt alkalmazni
* Ha mégis eltérnénk
    * Copy: független a kiindulási alaptól
    * Extend
        * You can only activate rules that are deactivated in the parent
        * Ha változik az eredeti, változik ez is
* Changelog
* Compare
* Back up (Restore a Quality Profiles / Java felületen)
* Set as default

# Quality Gate

* It focuses on keeping new code clean, rather than spending a 
 lot of effort remediating old code. 
* Conditions on New Code
* Actions
    * Create
    * Copy
* Projects

# Várakozás a Quality Gate-re

```
mvnw verify sonar:sonar -Dsonar.qualitygate.wait=true
```

* `sonar.qualitygate.timeout`
* Vagy SonarQube Scanner plugin, konfigurálása körülményesebb

# New Code

* Globálisan és projekt szinten is állítható (Project Settings / New Code)
* Lehetőségek
    * Previous Version (default)
    * Specific analysis
    * Reference Branch (Developer Edition)
    * Number of days

# Activity

* Analysis
* Quality Gate változott
* Profile változott
* Version változott
* Other

# Activity műveletek

* Create version
* Create custom event
* Delete analysis
    * Utolsót nem lehet
    * Következő analysis törli


# SonarLint

* Bind to SonarQube / SonarCloud (Configure the connection)
* Smart notification
    * Receive notification from SonarQube
    * Új issue jelenik meg
    * Elbukik a Quality Gate-en
* Csak felhasználónév/jelszóval működik, tokennel nem

# Lifecycle

* Open - set by SonarQube on new issues
* Confirmed - set manually to indicate that the issue is valid
* Resolved - set manually to indicate that the next analysis should Close the issue
* Reopened - set automatically by SonarQube when a Resolved issue hasn't actually been corrected
* Closed - set automatically by SonarQube for automatically created issues.

# Resolutions

* Fixed - pl. meg lett javítva
* Removed - rule lett kikapcsolva
* False Positive - set manually
* Won't Fix - set manually

# Actions

* Type change
* Severety change
* Status
    * Confirm - By confirming an issue, you're basically saying "Yep, that's a problem." Doing so moves it out of "Open" status to "Confirmed".
    * Resolve as fixed - If you think you've fixed an open issue, you can Resolve it. If you're right, the next analysis will move it to closed status. If you're wrong, its status will go to re-opened.
    * Resolve as False Positive - Looking at the issue in context, you realize that for whatever reason, this issue isn't actually a problem. So you mark it False Positive and move on. Requires Administer Issues permission on the project.
    * Resolve as Won't Fix - Looking at the issue in context, you realize that while it's a valid issue it's not one that actually needs fixing. In other words, it represents accepted technical debt. So you mark it Won't Fix and move on. Requires Administer Issues permission on the project.
    * Severity change - This is the middle ground between the first two options. Yes, it's a problem, but it's not as bad a problem as the rule's default severity makes it out to be. Or perhaps it's actually far worse. Either way, you adjust the severity of the issue to bring it in line with what you feel it deserves. Requires Administer Issues permission on the project.
* Assign
* Comment
* Tag hozzáadható, szerkeszthető

# Analízisok közötti issue megfeleltetés

* Sor hash-e alapján, kivéve whitespace

# Bulk change

# Security Hotspots

* Saját életciklusuk van: 
    * To review
    * Acknowledged 
    * Fixed
    * Safe
* Dokumentációjában három fül:
	What is the risk?
	Assess the risk
	How can I fix it?

# Tippek

* Ha sok false positive van, nem biztos, hogy jó a rule.
* Ha sokat kell állítani a súlyosságát, nem biztos, hogy jó a rule.

# Mail

* Mailhog teszteléshez

# Narrowing the focus

* Analysis körének megállapítása
* [Narrowing the focus](https://docs.sonarqube.org/latest/project-administration/narrowing-the-focus/)
* General settings / Analysis scope
* A. File Exclusions - elérési út, fájlnév alapján
* B. Code Coverage Exclusions - lefedettség
* D. Issue Exclusions - tartalom alapján
    * Ignore issues on file
        * Pontos egyezőség: `@Generated("com.example.generated")`
        * Regexp: `@Generated\(".*"\)`
    * Ignore Issues in Blocks
        * Start of block: `\s*//\s*START-NOSCAN`
        * End of block: `\s*//\s*END-NOSCAN`

```java
// START-NOSCAN
blahblah
// END-NOSCAN
```

# Ignore a fájlokban

* Összes: `@SuppressWarnings("all")`
* Rule-ok alapján: `@SuppressWarnings("java:S2077")` vagy `@SuppressWarnings({"java:S1118", "java:S3546"}).`

# Rule templates

* Rules / filter: `Template`
* Típusai
    * Custom resources should be closed
    * Track comments matching a regular expression
    * Track uses of disallowed classes
    * Track uses of disallowed constructors
    * Track uses of disallowed methods
* Törölhető

# Issue context

* Issues with multiple locations
* https://www.youtube.com/watch?v=17G-aZcuMKw

# Badges

* Project Information
* Ha kikerül: Renew Token

# Tags

* Project information
* Projects felületen szűrni lehessen rá

# Look&feel

* Logo
* Gravatar, alapból kikapcsolt állapotban

# Keyboard shortcuts

https://docs.sonarqube.org/latest/user-guide/keyboard-shortcuts/

# Biztonság

* Security
* Projects / Management

# Üzemeltetés - információk

* Administration/System

# Üzemeltetés - monitorozás

* Prometheus
* `http://localhost:9000/api/monitoring/metrics`

# Üzemeltetés - Housekeeping

* Administration / Configuration / Housekeeping
* Snapshotok törlése különböző szabályok alapján

# Üzemeltetés - Adatbázis

* Mentés: az adatbázishoz adott eszközökkel
* Átállás másid adatbázisra: SonarQube DB Copy Tool, csak supporttal vehető igénybe

# Üzemeltetés - Telemetry

* Küld adatokat a szervernek projekt szinten, anonimizálva, naponta egyszer

# Saját coding rules

* [Adding coding rules](https://docs.sonarqube.org/latest/extend/adding-coding-rules/)
* Formái
    * XPath
    * SonarQube API
    * [Generic Issue Reports](https://docs.sonarqube.org/latest/analysis/generic-issue/)


# Jenkins

# Nyelv beállítása

* Locale plugin
* Manage / Configure System / Locale
  * Default Language: `en`
  * Ignore browser preference and force this language to all users: checked

## Credentials

* _Manage Jenkins / Manage Credentials / (global)_
* _Add credential_
* _Kind_: _Secret text_
* _ID_: `sonar-token`

---

# Pipeline

```groovy
pipeline {
    agent any
    
    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'http://gitea:3000/vicziani/locations-app.git'

                // Run Maven on a Unix agent.
                sh "./mvnw -B clean verify sonar:sonar -Dsonar.projectKey=locations-app -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=${SONAR_TOKEN} -Dsonar.projectVersion=1.0.0-${BUILD_NUMBER}"
            }
        }
    }
}
```

# In repo

`Jenkinsfile`

```groovy
pipeline {
    agent any
    
    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Build') {
            steps {
                sh "./mvnw -B clean verify sonar:sonar -Dsonar.projectKey=locations-app -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=${SONAR_TOKEN} -Dsonar.projectVersion=1.0.0-${BUILD_NUMBER}"
            }
        }
    }
}
```

From SCM

# REST API

# Dokumentáció

http://localhost:9000/web_api
http://localhost:9000/api/system/health
http://localhost:9000/api/system/info

http://localhost:9000/api/metrics/search
http://localhost:9000/api/measures/component?metricKeys=ncloc,complexity,violations&component=locations-app

Generate User token

```http
### Get measures
GET http://localhost:9000/api/measures/component?metricKeys=ncloc,complexity,violations&component=employees-app
Authorization: Basic squ_1f2db0a9533c077155aadd72cd4a9e60cae398ce:
```

# Webhooks

https://beeceptor.com/

# Dependency-check-maven

* https://jeremylong.github.io/DependencyCheck/dependency-check-maven/
* National Vulnerability Database (NVD) hosted by NIST: https://nvd.nist.gov

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>7.3.2</version>

    <configuration>
        <formats>XML</formats>
    </configuration>
</plugin>
```

```
mvnw org.owasp:dependency-check-maven:check
```
