# pms-service

Service PMS en architecture hexagonale (Spring Boot + PostgreSQL + Flyway).

## Prerequis

```bash
export JAVA_HOME=/opt/homebrew/Cellar/openjdk/25.0.2/libexec/openjdk.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"
docker compose up -d
./mvnw clean test
./mvnw spring-boot:run
```

Tests:
- unit tests executes with `./mvnw test`
- integration tests use Testcontainers and run automatically when Docker is available

Versions:
- Java: `25`
- Spring Boot: `4.0.2`

Variables d'environnement utiles:

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `5432`)
- `DB_NAME` (default: `pms_db`)
- `DB_USERNAME` (default: `pms_user`)
- `DB_PASSWORD` (default: `pms_pass`)
- `SERVER_PORT` (default: `8484`)
- `PMS_DEMO_AUTO_SEED_ENABLED` (default: `true`)
- `PMS_DEMO_MIN_CLIENTS` (default: `1200`)
- `SECURITY_JWT_SECRET_BASE64` (base64 key JWT, default demo key)
- `SECURITY_JWT_EXPIRATION_SECONDS` (default: `28800`)

## Regles metier classification

- `CodeSituation=3` (Defaut): client qualite `7` ou `8`.
- `CodeSituation=3` (Defaut): client qualite `7` ou `8`, ou depassement strictement superieur a `90` jours.
- `CodeSituation=2` (Incident): impayes au-dessus de la marge ou qualite `6`.
- `CodeSituation=1` (Probation): client precedemment en defaut, sans incident courant, pendant 3 mois calendaires.
- `CodeSituation=0` (Sain): aucun critere incident/defaut/probation.

La table `pms_client_risk_state` conserve la date de dernier defaut pour piloter la probation.

## Securite

- Authentification: `POST /pms/v1/auth/authenticate`
- Format: `{ "login": "...", "password": "..." }`
- Reponse: `{ "accessToken": "...", "userName": "...", "roles": [...] }`
- Tous les endpoints `/pms/v1/**` (hors auth) exigent `Authorization: Bearer <token>`.

## API

- `POST /pms/v1/auth/authenticate`
- `GET /pms/v1/client/all`
- `GET /pms/v1/client`
- `GET /pms/v1/getDetailOD/{pcli}`
- `GET /pms/v1/getClassCustomerContagion?marge=15&observation=90&startFrom=2010-01-01&minId=1&maxId=100`
- `GET /pms/v1/getNumberCli`
- `GET /pms/v1/getCustomerOd/{pcli}`
- `GET /pms/v1/getCustomerLoans/{pcli}`
- `GET /pms/v1/getCustomerLoanDetail/{eve}/{age}/{dev}`
- `GET /pms/v1/getCustomerProfilDetail/{monthEndId}`
- `GET /pms/v1/simulateData1/{monthEndId}`
- `GET /pms/v1/extractDate`
- `GET /pms/v1/getextractDate`
