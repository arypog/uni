# Sistema de Biblioteca (Java)

Este projeto implementa um sistema de gerenciamento de biblioteca com persistência em arquivos, índices (B+ Tree e Hash Extensível) e interface de linha de comando.

## Requisitos
- Java 11 ou superior (recomendado OpenJDK)
- Nenhuma dependência externa

## Estrutura do Projeto
- `model/` — Entidades do domínio (Livro, Leitor, Categoria, Retira)
- `dao/` — Acesso a dados e lógica de persistência
- `controller/` — Lógica de negócio e orquestração
- `view/` — Interface de usuário (CLI)
- `base/` — Abstrações de arquivos e índices
- `index/` — Estruturas de indexação
- `dados/` — Arquivos binários de dados e índices (gerados em tempo de execução)
- `Main.java` — Ponto de entrada do sistema

## Passos para Rodar o Sistema
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/arypog/uni.git
   cd aeds/03/tp
    ```
2. **Compile todos os arquivos Java**
    ```sh
    javac */*.java Main.java
    ```
3. **Execute o sistema:**
    ```sh
    java Main
    ```
4. **Os arquivos de dados e índices serão criados automaticamente na pasta `dados/` ao rodar o sistema.**

## Observações
- Para reiniciar os dados, basta apagar o conteúdo da pasta dados/.
- O sistema não depende de banco de dados externo.
- Os índices e dados são persistidos em arquivos binários.
