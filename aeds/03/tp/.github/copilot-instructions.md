# Copilot Instructions for AI Agents

## Project Overview
This is a Java project for managing a library system. The system tracks books, users (leitores), categories (categorias), and loan/return operations (retira). The architecture is modular, with clear separation between data models, controllers, data access objects (DAOs), and views.

## Key Components
- `model/`: Domain entities (`Livro`, `Leitor`, `Categoria`, `Retira`).
- `controller/`: Business logic and orchestration (`ControleLivro`, `ControleLeitor`, etc.).
- `dao/`: Data access and persistence for each entity (`ArquivoLivro`, etc.).
- `base/`: Core abstractions for file storage, extensible hashing, and record management.
- `view/`: User interface logic for each entity.
- `index/` and `hash/`: Indexing and hashing structures for efficient data retrieval.
- `Main.java`: Entry point for the application.

## Data Flow & Patterns
- Controllers mediate between views and DAOs, enforcing business rules.
- DAOs handle low-level file operations, using classes in `base/` for storage and indexing.
- Models are simple POJOs with fields and basic methods.
- Views are responsible for user interaction and input/output.
- Indexing (B+ trees, extensible hashing) is implemented for efficient lookups, not via a database.

## Developer Workflows
- **Build/Run:** Compile and run with `javac`/`java` from the project root. No build scripts or external dependencies are present by default.
- **Debugging:** Focus on controller and DAO logic for most issues. Indexing bugs may require inspecting `index/` and `base/` classes.
- **Testing:** No explicit test suite; manual testing via the main application is expected.

## Project-Specific Conventions
- Portuguese naming for classes, methods, and variables (e.g., `Livro`, `Leitor`, `Retira`).
- File-based persistence: all data is stored in custom binary/text files, not a database.
- Indexing and hashing are implemented in-house (see `base/` and `index/`).
- Each entity has a dedicated controller, DAO, and view class.
- No frameworks or external libraries are used.

## Examples
- To add a new entity, create corresponding files in `model/`, `controller/`, `dao/`, and `view/`.
- To debug a book loan, inspect `ControleRetira.java`, `ArquivoRetira.java`, and `Retira.java`.
- For storage logic, see `base/Arquivo.java` and `base/HashExtensivel.java`.

## Integration Points
- All persistence is local file-based; no network or external service integration.
- Indexing and hashing are internal, not using third-party libraries.

## Quick Reference
- Main entry: `Main.java`
- Core logic: `controller/`, `dao/`, `base/`
- Data models: `model/`
- UI: `view/`

---
For questions about architecture or patterns, review the relevant controller, DAO, and model files for the entity in question.
