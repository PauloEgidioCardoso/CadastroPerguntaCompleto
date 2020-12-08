CREATE TABLE escola(
	codigo_escola SERIAL PRIMARY KEY NOT NULL,
	nome CHARACTER VARYING(50) NOT NULL,
	diretor CHARACTER VARYING(50) NOT NULL,
	CNPJ CHARACTER VARYING(20) NOT NULL
);
CREATE TABLE turma(
	codigo_turma SERIAL PRIMARY KEY NOT NULL,
	turno CHARACTER VARYING(20) NOT NULL,
	numero_turma CHARACTER VARYING(10) NOT NULL
);
CREATE TABLE endereco (
	codigo_endereco SERIAL PRIMARY KEY NOT NULL,
	codigo_usuario INTEGER NOT NULL REFERENCES usuario(codigo_usuario),
	estado CHARACTER VARYING(100) NOT NULL,
	cidade CHARACTER VARYING(100) NOT NULL,
	bairo CHARACTER VARYING(100) NOT NULL,
	rua CHARACTER VARYING(100) NOT NULL,
	numero CHARACTER VARYING(20) NOT NULL,
	complemento CHARACTER VARYING(20) NOT NULL,
	CEP CHARACTER VARYING(20) NOT NULL
);
CREATE TABLE usuario(
	codigo_usuario SERIAL PRIMARY KEY NOT NULL,
	nome CHARACTER VARYING(100) NOT NULL,
	data_nascimento DATE NOT NULL
);
CREATE TABLE professor(
	codigo_professor SERIAL PRIMARY KEY NOT NULL,
	codigo_usuario INTEGER NOT NULL REFERENCES usuario(codigo_usuario),
	CPF CHARACTER VARYING(14) NOT NULL	
);
CREATE TABLE aluno(
	codigo_aluno SERIAL PRIMARY KEY NOT NULL,
	codigo_usuario INTEGER NOT NULL REFERENCES usuario(codigo_usuario),
	codigo_turma INTEGER NOT NULL REFERENCES turma(codigo_turma),
	desempenho CHARACTER VARYING(50),
	media DECIMAL(4,2),
	serie INTEGER NOT NULL
);


CREATE TABLE pergunta(
	codigo_pergunta SERIAL PRIMARY KEY NOT NULL,
	alternativa_1 CHARACTER VARYING(1000),
	alternativa_2 CHARACTER VARYING(1000),
	alternativa_3 CHARACTER VARYING(1000),
	alternativa_4 CHARACTER VARYING(1000),
	conteudo_pergunta CHARACTER VARYING(1000) NOT NULL,
	dificuldade CHARACTER VARYING(20) NOT NULL,
	resposta CHARACTER VARYING(1000) NOT NULL,
	dica CHARACTER VARYING(1000) NOT NULL
);

CREATE TABLE indicacaodificuldade(
	codigo_indicacao SERIAL PRIMARY KEY NOT NULL,
	serie CHARACTER VARYING(20) NOT NULL
);

CREATE TABLE pergunta_indicacaodificuldade(
	codigo_indicacao INTEGER NOT NULL REFERENCES indicacaodificuldade(codigo_indicacao),
	codigo_pergunta INTEGER NOT NULL REFERENCES pergunta(codigo_pergunta),
	CONSTRAINT codigo_p_id PRIMARY KEY (codigo_indicacao, codigo_pergunta)
);

CREATE TABLE escola_professor(
	codigo_escola INTEGER NOT NULL REFERENCES escola(codigo_escola),
	codigo_professor INTEGER NOT NULL REFERENCES professor(codigo_professor),
	CONSTRAINT escola_professor_pk PRIMARY KEY (codigo_escola,codigo_professor)
);
CREATE TABLE professor_turma(
	codigo_turma INTEGER NOT NULL REFERENCES turma(codigo_turma),
	codigo_professor INTEGER NOT NULL REFERENCES professor(codigo_professor),
	CONSTRAINT professor_turma_pk PRIMARY KEY (codigo_turma,codigo_professor)
);
CREATE TABLE aluno_pergunta(
	codigo_aluno INTEGER NOT NULL REFERENCES aluno(codigo_aluno),
	codigo_pergunta INTEGER NOT NULL REFERENCES pergunta(codigo_pergunta),
	CONSTRAINT aluno_pergunta_pk PRIMARY KEY (codigo_aluno,codigo_pergunta) 
);
