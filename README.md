# Task Manager Pro - Java 21

Um aplicativo de gerenciamento de tarefas moderno e elegante desenvolvido em Java 21, utilizando Swing para a interface gráfica e recursos modernos da linguagem.

![Java](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Active-success)

## 📋 Sobre o Projeto

Task Manager Pro é uma aplicação desktop para gerenciamento de tarefas que permite adicionar, editar, remover e listar suas atividades diárias de forma simples e intuitiva. O projeto foi desenvolvido com foco em boas práticas de programação e recursos modernos do Java 21.

## ✨ Funcionalidades

- ✅ **Adicionar Tarefas** - Crie novas tarefas com descrição, observação, prioridade, categoria e data de vencimento
- ✏️ **Editar Tarefas** - Modifique todos os campos de tarefas existentes
- ❌ **Remover Tarefas** - Exclua tarefas com confirmação de segurança
- 📋 **Listar Tarefas** - Visualize todas as suas tarefas em uma lista organizada
- ✓ **Marcar/Desmarcar Concluídas** - Alterne o status de conclusão das tarefas facilmente
- 🔍 **Filtrar Tarefas** - Visualize todas, apenas pendentes ou apenas concluídas
- ⚡ **Prioridades** - Defina prioridades (Baixa, Média, Alta) para suas tarefas
- 🏷️ **Categorias/Tags** - Organize tarefas por categorias personalizadas
- 📅 **Data de Vencimento** - Adicione prazos às suas tarefas
- 💭 **Observações** - Adicione notas detalhadas que podem ser editadas ao clicar na tarefa
- 📊 **Exportar CSV/JSON** - Exporte suas tarefas para outros formatos
- 🌓 **Tema Claro/Escuro** - Alterne entre temas para melhor conforto visual
- ⌨️ **Atalhos de Teclado** - Navegue e execute ações rapidamente
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
   - `observacao`: String com observações detalhadas
   - `prioridade`: Enum (BAIXA, MEDIA, ALTA)
   - `categoria`: String com a categoria da tarefa
   - `dataVencimento`: LocalDate com a data de vencimento

2. **TarefaService.java** - Camada de serviço
   - Gerencia a lista de tarefas
   - Implementa operações CRUD (Create, Read, Update, Delete)
   - Filtragem de tarefas (todas, pendentes, concluídas)
   - Exportação para CSV e JSON
   - Responsável pela persistência em arquivo binário

3. **TodoApp.java** - Interface gráfica
   - Janela principal (JFrame)
   - Componentes Swing customizados
   - Event handlers para interação do usuário
   - Sistema de temas (claro/escuro)
   - Atalhos de teclado para maior produtividade

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

- **Header** - Cabeçalho azul com título em destaque e filtros
- **Filtros** - ComboBox para filtrar tarefas (Todas, Pendentes, Concluídas)
- **Lista Central** - Área scrollável para visualizar tarefas com prioridades e categorias
- **Botões de Ação** - Grid 2x3 com botões coloridos:
  - 🟢 Verde: Adicionar Nova Tarefa
  - 🟠 Laranja: Editar Tarefa Selecionada
  - 🔴 Vermelho: Remover Tarefa
  - 🔵 Azul: Marcar/Desmarcar como Concluída
  - 🟣 Rosa: Ver Detalhes e Observações
  - ⚫ Cinza: Atualizar Lista
- **Botões de Exportação** - Exportar tarefas para CSV ou JSON
- **Interações**:
  - Clique duplo em uma tarefa para ver detalhes
  - Espaço para marcar/desmarcar conclusão
  - Enter para ver detalhes
  - Atalhos de teclado para todas as ações principais

### Paleta de Cores

- **Primary Color**: `#6366F1` (Indigo)
- **Success Color**: `#10B981` (Green)
- **Warning Color**: `#F59E0B` (Amber)
- **Danger Color**: `#EF4444` (Red)
- **Background**: `#F9FAFB` (Light Gray)

## 💡 Recursos Modernos do Java 21

### Records (Java 14+)
```java
public record Tarefa(
    String descricao, 
    boolean concluida,
    String observacao,
    Prioridade prioridade,
    String categoria,
    LocalDate dataVencimento
) implements Serializable {
    // Construtor para compatibilidade retroativa
    public Tarefa(String descricao, boolean concluida) {
        this(descricao, concluida, "", Prioridade.MEDIA, "Geral", null);
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

## ⌨️ Atalhos de Teclado

Navegue e execute ações rapidamente usando atalhos:

| Atalho | Ação |
|--------|------|
| `Ctrl+N` | Nova tarefa |
| `Ctrl+E` | Editar tarefa selecionada |
| `Delete` | Remover tarefa selecionada |
| `Space` | Marcar/desmarcar como concluída |
| `Enter` | Ver detalhes e observações |
| `Ctrl+R` | Atualizar lista |
| `Ctrl+T` | Alternar tema claro/escuro |

## 💭 Como Usar Observações

As observações são notas detalhadas que você pode adicionar às suas tarefas:

1. **Ao criar uma tarefa**: Preencha o campo "Observação" no diálogo de nova tarefa
2. **Ao editar uma tarefa**: Edite o campo de observação junto com outros campos
3. **Visualizar observações**: 
   - Clique duas vezes na tarefa, ou
   - Selecione a tarefa e pressione `Enter`, ou
   - Clique no botão "📋 Detalhes"
4. **Editar somente a observação**: No diálogo de detalhes, clique em "✏️ Editar Observação"

## 📊 Exportação de Dados

Exporte suas tarefas para uso em outras aplicações:

- **CSV**: Formato compatível com Excel, Google Sheets e outros
- **JSON**: Formato estruturado para integração com outras aplicações

Os arquivos exportados incluem todos os campos: descrição, status, observação, prioridade, categoria e data de vencimento.

## 🎯 Melhorias Implementadas

- [X] Marcar/desmarcar tarefas como concluídas ✅
- [X] Filtrar tarefas (todas, pendentes, concluídas) ✅
- [X] Adicionar prioridades às tarefas ✅
- [X] Implementar categorias/tags ✅
- [X] Adicionar data de vencimento ✅
- [X] Adicionar observações às tarefas ✅
- [X] Editar observações separadamente ✅
- [X] Exportar tarefas para CSV/JSON ✅
- [X] Tema claro/escuro ✅
- [X] Atalhos de teclado ✅
- [X] Visualização detalhada de tarefas ✅

## 🎯 Próximas Melhorias

- [ ] Notificações de tarefas próximas ao vencimento
- [ ] Busca e pesquisa de tarefas
- [ ] Estatísticas e gráficos de produtividade
- [ ] Subtarefas e checklist
- [ ] Sincronização com nuvem
- [ ] Anexos de arquivos
- [ ] Histórico de alterações

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!

