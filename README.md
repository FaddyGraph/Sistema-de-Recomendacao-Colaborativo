# 🎬 Sistema de Recomendação Colaborativo

Este repositório contém a implementação de um **Sistema de Recomendação Colaborativo**, desenvolvido como projeto prático para a disciplina de **Conceitos de Linguagens de Programação (CLP)** sob orientação do Prof. Aléssio M. Jr..

O objetivo é explorar diferentes paradigmas de programação para resolver o mesmo problema computacional, analisando como a estrutura da linguagem influencia a implementação da lógica de negócios.

---

## O Problema
O sistema visa recomendar filmes a usuários baseando-se no comportamento de "vizinhos" (usuários com gostos similares). O projeto é dividido em três fases de complexidade crescente:

### Fase 1: Aquecimento e Consultas
Foco em sintaxe e estruturas de dados básicas. Implementação de consultas como:
* Listar filmes por gênero.
* Filtrar filmes por ano de lançamento.
* Recuperar histórico de notas de um usuário específico.

### Fase 2: Lógica Intermediária
Processamento de listas e aplicação de regras de negócio:
* Cálculo de média de avaliações.
* Identificação de perfis específicos (ex: `usuario_hater`).
* Cruzamento de dados entre usuários (`diretores_comuns`).

### Fase 3: Motor Colaborativo
O desafio final envolve a implementação da inteligência de recomendação:
1. **Similaridade:** Identificar vizinhos que deram notas altas ($\ge 4$) aos mesmos filmes.
2. **Candidatos:** Sugerir filmes bem avaliados pelos vizinhos que o usuário alvo ainda não viu.
3. **Filtragem de Ódio:** Regra de exclusão automática de diretores baseada em avaliações negativas (nota 1) do usuário alvo.

---

## 🛠️ Paradigmas Explorados

Para demonstrar a versatilidade dos conceitos de CLP, o problema foi abordado em três frentes:

| Paradigma | Linguagem | Foco Técnico |
| :--- | :--- | :--- |
| **Orientado a Objetos** | Java | Implementação completa, modularidade e gerenciamento de estado no Heap. |
| **Programação Lógica** | Prolog | Inferência de regras e fatos para busca de similaridade. |
| **Programação Funcional** | Lisp | Processamento de listas, imutabilidade e funções de alta ordem. |

---

## ⚙️ Diferenciais Técnicos (Conceitos de CLP)

* **Implementação Híbrida:** O uso de Java permite observar a otimização JIT (Just-In-Time) e a portabilidade via JVM.
* **Amarração (Binding):** Comparação entre a amarração estática de tipos em Java e a amarração dinâmica/tardia em outras etapas do projeto.
* **Gerenciamento de Memória:** Otimização do uso do Monte (Heap) para processar grandes bases de dados (CSV) e evitar sobrecarga do Garbage Collector.
* **Persistência e Serialização:** Estudo de como transformar objetos em memória em dados persistentes para carga posterior.

---
## 📚 Créditos e Referências

* **Professor: Aléssio M. Jr.**
* **Disciplina: Conceitos de Linguagens de Programação (CLP).**
* **Documentação Oficial:** [Descrição do Problema](https://www.alessiojr.com/disciplines/clp/trabalho/descricao-problema.html)
