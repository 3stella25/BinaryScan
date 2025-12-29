## BinaryScan

BinaryScan is a Java-based application that integrates multiple RESTful APIs to analyze and enrich user-provided input with external intelligence. The project focuses on clean API integration, structured JSON handling, and a simple, interactive desktop interface.

At a high level, BinaryScan consumes **two RESTful APIs** to retrieve metadata and reputation signals, processes the responses in Java, and presents actionable results to the user via a JavaFX UI.

---

## Tech Stack

* **Java** – Core application logic and API integration
* **RESTful APIs** – External data sources accessed over HTTP
* **JSON** – Request/response payload format
* **JavaFX** – Desktop user interface
* **CSS** – UI styling for JavaFX components
* **Maven** – Dependency management and build automation
* **Bash** – Setup and run scripts
* **Git** – Version control

---

## External APIs Used

BinaryScan relies on third-party APIs to enrich input data. These APIs are **consumed only**; BinaryScan does not redistribute raw data beyond displaying results to the end user.

### 1. VirusTotal API

**Purpose:**
Provides threat intelligence and reputation data by aggregating results from multiple antivirus engines and security vendors. VirusTotal helps assess whether a URL, domain, or IP address has been associated with malicious activity.

**API Link:**
[https://docs.virustotal.com/reference/overview](https://docs.virustotal.com/reference/overview)

**How it is used:**

* User input (URL, domain, or IP address) is validated locally
* A REST request is sent to the VirusTotal API endpoint
* JSON responses are parsed and mapped to internal Java models
* Relevant detection and reputation indicators are displayed in the UI

**Typical data retrieved:**

* Malicious / suspicious detection counts
* Reputation or risk indicators
* Associated security vendor results

---

### 2. IPWhois API

**Purpose:**
Retrieves WHOIS and geolocation metadata for IP addresses, providing contextual information such as ownership, ASN, and registration details.

**API Link:**
[https://ipwhois.io/](https://ipwhois.io/)

**How it is used:**

* Queried after basic input validation
* Used to enrich VirusTotal results with ownership and network context
* Information is summarized and displayed in a user-friendly format

**Typical data retrieved:**

* IP owner / organization
* ASN and ISP information
* Country and region data

---

## API Usage Guidelines

BinaryScan follows responsible and compliant API usage practices:

* **Read-only usage:** APIs are queried strictly for information retrieval
* **No data persistence:** Third-party API responses are not stored long-term
* **Rate limiting:** Requests are kept within reasonable limits to avoid abuse
* **Input validation:** User input is sanitized before being sent to any external service
* **Attribution:** External APIs are acknowledged in this README and project documentation

API keys (if required) are **not hardcoded** and should be provided via environment variables or configuration files excluded from version control.

---

## Screenshots

Below are example screenshots demonstrating BinaryScan in action. Screenshots should be placed in a `/screenshots` directory at the root of the repository.

```md
![BinaryScan Main Interface](screenshots/main-ui.png)
![VirusTotal Results View](screenshots/virustotal-results.png)
![IPWhois Details View](screenshots/ipwhois-results.png)
```

---

## Project Goals

* Demonstrate practical RESTful API integration in Java
* Practice structured JSON parsing and data modeling
* Combine backend logic with a clean JavaFX frontend
* Apply secure and ethical API consumption practices

---

## Disclaimer

BinaryScan is an educational project. Results provided by third-party APIs are informational only and should not be treated as definitive security assessments.
