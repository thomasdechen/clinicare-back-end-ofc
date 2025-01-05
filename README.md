# Clinicare Back-end <img src="https://cdn.worldvectorlogo.com/logos/spring-3.svg" width="60px" height="60px" alt="Logo do Angular">

📋 **Visão Geral**

CliniCare é um sistema web robusto para gerenciamento de clínicas médicas, desenvolvido com Java Spring. Este repositório contém o código backend do projeto, responsável por toda a lógica de negócios e interações com o banco de dados.

✨ **Funcionalidades Principais**

👤 Cadastro e autenticação de usuários (Pacientes, Médicos e Secretários)  
📝 Gestão de perfis de usuários  
🗓️ Agendamento e cancelamento de consultas  
🏷️ Postagem e gerenciamento de serviços médicos  
⭐ Sistema de avaliação de médicos  
🔐 Geração de códigos para cadastro de médicos e secretários  

🛠️ **Tecnologias Utilizadas**

Java 17  
Spring Boot  
Spring Data JPA  
Spring Security  
PostgreSQL  
Maven  

🚀 **Configuração e Instalação**

Clone o repositório:  
git clone https://github.com/thomasdechen/clinicare-back-end-ofc.git  

Configure o banco de dados no arquivo application.properties.  
Segue exemplo de application.properties:  

    spring.datasource.url=jdbc:postgresql://localhost:5432/clinicare  
    spring.datasource.username=[username]  
    spring.datasource.password=[password]  
    
    api.security.token.secret=my-secret-key -- Inicialização token  
    
    file.upload-dir=/path/to/upload/directory/  

    #Informações de usuário, password e token, caso for uma aplicação  
    #que vai ser feito deploy, é melhor configurar por variáveis de ambiente e  
    #depois puxar aqui no application properties as variáveis de ambiente para ter mais segurança.  


🔗 **API Endpoints**
Aqui estão alguns dos principais endpoints da API:

### 🔐 Autenticação
- `POST /auth/login`: Realiza login do usuário
- `POST /auth/register`: Registra um novo usuário
- `POST /auth/refresh-token`: Atualiza o token de autenticação

### 👤 Usuários
- `GET /user/profile`: Obtém o perfil do usuário autenticado
- `GET /user/profile/{id}`: Obtém o perfil de um usuário específico
- `PUT /user/profile/{id}`: Atualiza o perfil de um usuário
- `DELETE /user/profile/{id}`: Deleta o perfil de um usuário

### 🏥 Pacientes
- `GET /user/paciente/{id}`: Obtém o perfil de um paciente
- `PUT /user/paciente/{id}`: Atualiza o perfil de um paciente
- `DELETE /user/paciente/{id}`: Deleta o perfil de um paciente

### 👨‍⚕️ Médicos
- `GET /user/medico`: Lista todos os médicos
- `GET /user/medico/{id}`: Obtém o perfil de um médico
- `PUT /user/medico/{id}`: Atualiza o perfil de um médico
- `DELETE /user/medico/{id}`: Deleta o perfil de um médico

### 👩‍💼 Secretários
- `GET /user/secretario/{id}`: Obtém o perfil de um secretário
- `PUT /user/secretario/{id}`: Atualiza o perfil de um secretário
- `DELETE /user/secretario/{id}`: Deleta o perfil de um secretário

### 📅 Agendamentos
- `POST /agendamento/criar`: Cria um novo agendamento
- `GET /agendamento/paciente/{id}`: Lista agendamentos de um paciente
- `GET /agendamento/medico/{id}`: Lista agendamentos de um médico
- `GET /agendamento/secretario/{medicoId}`: Lista agendamentos dos médicos de um secretário
- `PUT /agendamento/cancelar/{id}`: Cancela um agendamento

### 🏷️ Serviços
- `GET /servico/medico/{id}/servicos`: Lista serviços de um médico
- `GET /servico`: Lista todos os serviços
- `POST /servico/criar`: Cria um novo serviço
- `GET /servico/{id}`: Obtém detalhes de um serviço
- `PUT /servico/atualizar/{id}`: Atualiza um serviço
- `DELETE /servico/deletar/{id}`: Deleta um serviço

### 🕒 Disponibilidade
- `GET /disponibilidade/medico/{id}/dias`: Lista dias disponíveis de um médico
- `POST /disponibilidade/atualizar/{id}`: Atualiza disponibilidade de um médico
- `GET /disponibilidade/medico/{id}/dia/{dia}`: Lista horários disponíveis em um dia específico

### ⭐ Avaliações
- `POST /avaliacao/criar`: Cria uma nova avaliação
- `GET /avaliacao/medico/{idMedico}`: Lista avaliações de um médico
- `GET /avaliacao/verificar/{idPaciente}/{idMedico}`: Verifica existência de avaliação
- `PUT /avaliacao/alterar/{id}`: Altera uma avaliação
- `DELETE /avaliacao/excluir/{id}`: Exclui uma avaliação

🔒 **Segurança**
O sistema utiliza autenticação baseada em tokens JWT e implementa controle de acesso baseado em funções (RBAC) para garantir que apenas usuários autorizados possam acessar recursos específicos.

🧪 **Testes**  
Teste serão implementados...  

Segue vídeo de demonstração do site: [https://youtu.be/_mQD28slnKc](https://youtu.be/DrQGDmrTAas)

📞 **Contato**  
Thomas Dechen Ferreira - dechendev@gmail.com  
Link do backend: https://github.com/thomasdechen/clinicare-back-end-ofc  
Link do frontend: https://github.com/thomasdechen/Clinicare-front-ofc  

⭐️ Se gostou do projeto, não se esqueça de dar uma estrela!  
