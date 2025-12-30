# Task Manager Pro - Java 21

Um aplicativo de gerenciamento de tarefas moderno e elegante desenvolvido em Java 21, utilizando Swing para a interface gráfica e recursos modernos da linguagem.

![Java](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Active-success)

## 📋 Sobre o Projeto

Task Manager Pro é uma aplicação desktop para gerenciamento de tarefas que permite adicionar, editar, remover e listar suas atividades diárias de forma simples e intuitiva. O projeto foi desenvolvido com foco em boas práticas de programação e recursos modernos do Java 21.

## ✨ Funcionalidades

- ✅ **Adicionar Tarefas** - Crie novas tarefas com descrição personalizada
- ✏️ **Editar Tarefas** - Modifique a descrição de tarefas existentes
- ❌ **Remover Tarefas** - Exclua tarefas com confirmação de segurança
- 📋 **Listar Tarefas** - Visualize todas as suas tarefas em uma lista organizada
- 💾 **Persistência de Dados** - As tarefas são salvas automaticamente em arquivo binário
- 🎨 **Interface Moderna** - Design limpo e profissional com cores vibrantes

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Swing** - Framework para interface gráfica
- **Records** - Modelo de dados imutável (Java 14+)
- **Text Blocks** - Strings multilinha (Java 15+)
- **Switch Expressions** - Sintaxe moderna com arrow (Java 14+)
- **NIO.2** - API moderna de I/O para manipulação de arquivos
- **Serialização** - Persistência de dados em formato binário

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas com separação de responsabilidades:

```
TodoApp/
├── src/
│   ├── TodoApp.java       # Interface gráfica (View + Controller)
│   ├── TarefaService.java # Lógica de negócio e persistência (Service)
│   └── Tarefa.java        # Modelo de dados (Record)
├── out/
│   └── production/
│       └── TodoApp/       # Classes compiladas
├── tarefas.dat            # Arquivo de dados (gerado automaticamente)
└── README.md
```

### Componentes Principais

1. **Tarefa.java** - Record imutável que representa uma tarefa
   - `descricao`: String com a descrição da tarefa
   - `concluida`: Boolean indicando se está concluída

2. **TarefaService.java** - Camada de serviço
   - Gerencia a lista de tarefas
   - Implementa operações CRUD (Create, Read, Update, Delete)
   - Responsável pela persistência em arquivo binário

3. **TodoApp.java** - Interface gráfica
   - Janela principal (JFrame)
   - Componentes Swing customizados
   - Event handlers para interação do usuário

## 📦 Como Executar

### Pré-requisitos

- Java Development Kit (JDK) 21 ou superior
- Sistema operacional: Windows, macOS ou Linux

### Compilação

```bash
# Clone o repositório
git clone https://github.com/rafaeldiasgarcia/TodoApp.git
cd TodoApp/TodoApp

# Compile os arquivos Java
javac -d out/production/TodoApp src/*.java
```

### Execução

```bash
# Execute a aplicação
java -cp out/production/TodoApp TodoApp
```

Ou em uma única linha:

```bash
cd TodoApp && javac -d out/production/TodoApp src/*.java && java -cp out/production/TodoApp TodoApp
```

## 🎨 Interface do Usuário

A interface foi desenvolvida com foco em usabilidade e design moderno:

- **Header** - Cabeçalho azul com título em destaque
- **Lista Central** - Área scrollável para visualizar todas as tarefas
- **Botões de Ação** - Grid 2x2 com botões coloridos:
  - 🟢 Verde: Adicionar
  - 🟠 Laranja: Editar
  - 🔴 Vermelho: Remover
  - 🔵 Azul: Atualizar

### Paleta de Cores

- **Primary Color**: `#6366F1` (Indigo)
- **Success Color**: `#10B981` (Green)
- **Warning Color**: `#F59E0B` (Amber)
- **Danger Color**: `#EF4444` (Red)
- **Background**: `#F9FAFB` (Light Gray)

## 💡 Recursos Modernos do Java 21

### Records (Java 14+)
```java
public record Tarefa(String descricao, boolean concluida) implements Serializable {
    @Override
    public String toString() {
        return (concluida ? "[X] " : "[ ] ") + descricao;
    }
}
```

### Text Blocks (Java 15+)
```java
String html = """
    <html>
    <body>
    <h2>Task Manager Pro</h2>
    </body>
    </html>
    """;
```

### Switch Expressions (Java 14+)
```java
switch (opcao) {
    case "1" -> adicionar();
    case "2" -> listar();
    case "3" -> remover();
    default -> mensagem("Opção inválida!");
}
```

### NIO.2 (Java 7+)
```java
Path path = Paths.get("tarefas.dat");
try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
    oos.writeObject(tarefas);
}
```

## 📝 Persistência de Dados

As tarefas são salvas automaticamente no arquivo `tarefas.dat` usando serialização Java. O arquivo é:
- Criado automaticamente na primeira execução
- Atualizado a cada operação (adicionar, editar, remover)
- Carregado automaticamente ao iniciar a aplicação

## 🤝 Como Contribuir

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**Rafael Dias Garcia**

- GitHub: [@rafaeldiasgarcia](https://github.com/rafaeldiasgarcia)
- Repositório: [TodoApp](https://github.com/rafaeldiasgarcia/TodoApp)

## 🎯 Melhorias Futuras

- [ ] Marcar/desmarcar tarefas como concluídas
- [ ] Filtrar tarefas (todas, pendentes, concluídas)
- [ ] Adicionar prioridades às tarefas
- [ ] Implementar categorias/tags
- [ ] Adicionar data de vencimento
- [ ] Exportar tarefas para CSV/JSON
- [ ] Tema claro/escuro
- [ ] Atalhos de teclado

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!

