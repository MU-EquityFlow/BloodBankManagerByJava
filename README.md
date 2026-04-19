# 🩸 Blood Bank Manager

A desktop application for managing blood bank operations, built with **Java Swing**. It supports donor registration, blood stock tracking, blood requests, and persistent JSON-based storage — all backed by classic **Gang of Four design patterns**.

---

## 📋 Table of Contents

- #features
- #ui-panels
- #design-patterns
- #project-structure
- #getting-started
- #requirements
- #how-to-run
- #storage
- #technologies-used

---

## Features

- **Donor Registration** — Add new blood donors with name, blood group, phone, and date
- **Donor List** — View all registered donors in a sortable table
- **Blood Stock Management** — Track inventory for all 8 blood groups (A+, A−, B+, B−, AB+, AB−, O+, O−)
- **Blood Request** — Submit and process blood requests; stock is deducted automatically
- **Undo Support** — Command history allows undoing donor additions and blood requests
- **Persistent Storage** — All data is saved to local JSON files automatically

---

## UI Panels

| Panel | Description |
|---|---|
| **Home** | Welcome screen / dashboard overview |
| **Register Donor** | Form to register a new blood donor |
| **View Donors** | Table listing all registered donors |
| **View Stock** | Current blood unit counts per blood group |
| **Request Blood** | Form to submit a blood request |

Navigation is handled via a sidebar with buttons that switch panels using a `CardLayout`.

---

## Design Patterns

This project is intentionally built to demonstrate multiple **Gang of Four (GoF)** design patterns:

| Pattern | Where Used | Purpose |
|---|---|---|
| **Singleton** | `Dashboard` | Ensures a single application window instance |
| **Command** | `AddDonorCommand`, `RequestBloodCommand`, `CommandHistory` | Encapsulates actions and enables undo |
| **Facade** | `NavigationFacade` | Simplifies panel-switching across the `CardLayout` |
| **Factory** | `UIFactory` | Centralises creation of styled Swing components |
| **Memento** | `StockMemento`, `Stocking` | Captures and restores blood stock state for rollback |
| **Adapter** | `JsonAdapter`, `DataConverter` | Converts between domain models and JSON storage format |

---

## Project Structure

```
BloodBankManagerByJava/
├── src/
│   ├── model/
│   │   ├── Donor.java              # Donor data model
│   │   ├── BloodStock.java         # Blood inventory model (with Memento support)
│   │   └── BloodRequest.java       # Blood request model
│   ├── patterns/
│   │   ├── command/
│   │   │   ├── Command.java            # Command interface (execute + undo)
│   │   │   ├── AddDonorCommand.java    # Command for adding a donor
│   │   │   ├── RequestBloodCommand.java# Command for requesting blood
│   │   │   └── CommandHistory.java     # Undo stack
│   │   ├── data/
│   │   │   ├── JsonAdapter.java        # Adapter: domain ↔ JSON
│   │   │   ├── JsonStorage.java        # Reads/writes JSON files
│   │   │   └── DataConverter.java      # Helper conversions
│   │   ├── memento/
│   │   │   ├── StockMemento.java       # Snapshot of blood stock state
│   │   │   └── Stocking.java           # Originator that creates/restores mementos
│   │   ├── NavigationFacade.java       # Facade for CardLayout navigation
│   │   └── UIFactory.java              # Factory for Swing UI components
│   └── ui/
│       ├── Main.java                   # Entry point
│       ├── Dashboard.java              # Singleton main JFrame
│       ├── HomePanel.java
│       ├── DonorRegistrationPanel.java
│       ├── DonorListPanel.java
│       ├── BloodStockPanel.java
│       └── BloodRequestPanel.java
└── storage/
    ├── donors.json                     # Persisted donor records
    ├── stock.json                      # Persisted blood stock levels
    └── requests.json                   # Persisted blood request records
```

---

## Getting Started

### Requirements

- **Java 11** or higher (Java 17+ recommended)
- **IntelliJ IDEA** (recommended — project includes `.iml` and `.idea` config)
- Or any IDE / build tool that supports plain Java projects

### How to Run

**Option 1 — IntelliJ IDEA**

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/BloodBankManagerByJava.git
   ```
2. Open the project folder in IntelliJ IDEA.
3. Mark `src/` as the **Sources Root** if not already set.
4. Run `src/ui/Main.java`.

**Option 2 — Command Line**

```bash
# Compile
javac -d out $(find src -name "*.java")

# Run
java -cp out ui.Main
```

---

## Storage

Data is persisted as JSON files inside the `storage/` directory at the project root:

| File | Contents |
|---|---|
| `donors.json` | Array of registered donor objects |
| `stock.json` | Map of blood group → unit count |
| `requests.json` | Array of blood request records |

These files are created/updated automatically when the application is used. No database setup is required.

---

## Technologies Used

- **Java** — Core language
- **Java Swing** — Desktop GUI framework
- **JSON** — Lightweight persistent storage (via custom `JsonAdapter` / `JsonStorage`)
- **IntelliJ IDEA** — Development environment
- **GoF Design Patterns** — Singleton, Command, Facade, Factory, Memento, Adapter

---

## License

This project is open-source and available under the [MIT License](LICENSE).
