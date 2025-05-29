## 🧬 Algoritmo Genético para Geração de Horários

Este projeto implementa um **Algoritmo Genético** para alocar professores a disciplinas de forma a minimizar conflitos de horários, utilizando uma abordagem baseada em genes e operadores genéticos (seleção, crossover e mutação). O resultado é apresentado em uma interface Swing e exportado em **formato HTML interativo**.

---

### 🚀 Funcionalidades

* Associação automática entre professores e disciplinas.
* Execução do algoritmo genético com:

  * População inicial randômica
  * Seleção por torneio competitivo
  * Crossover por períodos aleatórios
  * Mutação controlada por taxa configurável
* Histórico completo de todas as gerações
* Exportação em HTML com:

  * População
  * Pais selecionados
  * Filhos gerados
  * Conflitos destacados em vermelho
* Interface gráfica com visualização por geração

---

### 🛠️ Tecnologias

* **Java 17+**
* **Swing** (interface gráfica)
* **HTML** para visualização de resultados
* Estrutura modular:

  * `models/` (Entidades)
  * `generator/` (Lógica do algoritmo)
  * `ui/` (Interface)

---

### 📁 Organização do Código

| Pasta/Arquivo       | Função Principal                                  |
| ------------------- | ------------------------------------------------- |
| `models/`           | Classes como `Individuo`, `Geracao`, `Professor`  |
| `generator/`        | Núcleo do algoritmo genético                      |
| `ui/InterfaceSwing` | Interface com botão para rodar AG e ver histórico |
| `GeradorHTML.java`  | Exporta os dados da geração em um HTML navegável  |

---

### 🧪 Como Executar

1. Compile o projeto no VSCode ou outro IDE com suporte a Java.
2. Execute `InterfaceSwing.java`.
3. Clique em **"Executar AG e Ver Horário"**.
4. Navegue pelas gerações usando o menu suspenso.
5. Clique em **"Abrir HTML"** para visualizar o resultado detalhado.

---

### ✅ Exemplo de Saída HTML

* Mostra os genes (alocação) de cada indivíduo.
* Conflitos de alocação são destacados com fundo vermelho.
* Títulos indicam claramente:

  * População
  * Pais Selecionados
  * Filhos Gerados

---

### 📌 Observações

* O HTML gerado é salvo como `melhor_horario.html` ou `geracao_<N>.html`.
* O algoritmo pode ser ajustado para diferentes critérios de seleção e mutação.
* A visualização suporta múltiplas execuções sem sobrescrever os arquivos anteriores.


