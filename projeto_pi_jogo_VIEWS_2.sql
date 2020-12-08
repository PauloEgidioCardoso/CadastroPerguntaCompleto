CREATE VIEW view_usuario AS SELECT codigo_usuario, nome, data_nascimento FROM usuario
SELECT * FROM view_usuario
;
CREATE VIEW view_professor AS SELECT codigo_professor, codigo_usuario, cpf FROM professor
SELECT * FROM view_professor
;
CREATE VIEW view_escola AS SELECT codigo_escola, nome, diretor, cnpj FROM escola
SELECT * FROM view_escola
;
CREATE VIEW view_aluno AS SELECT codigo_aluno, codigo_usuario, codigo_turma, desempenho, media, serie FROM aluno
SELECT * FROM view_aluno
;
CREATE VIEW view_endereco AS SELECT codigo_endereco, codigo_usuario, estado, cidade, bairo, rua, numero, complemento, cep FROM endereco
SELECT * FROM view_endereco
;
SELECT e.codigo_endereco, e.estado, e.cidade, e.bairo FROM view_endereco AS e
SELECT e.codigo_endereco, e.estado, e.cidade, e.bairo, a.desempenho, a.media, a.serie FROM view_endereco AS e, view_aluno AS a 
SELECT codigo_aluno FROM view_aluno INNER JOIN view_professor ON view_aluno.codigo_aluno=view_professor.codigo_professor
;
SELECT u.codigo_usuario, u.nome, e.codigo_endereco, e.codigo_usuario FROM view_usuario AS u 
INNER JOIN view_endereco AS e ON u.codigo_usuario=e.codigo_usuario
;
SELECT u.codigo_usuario, u.nome, e.codigo_endereco, e.codigo_usuario, e.estado, e.cidade, e.bairo, e.rua, e.numero, e.cep FROM view_usuario AS u 
INNER JOIN view_endereco AS e ON u.codigo_usuario=e.codigo_usuario

CREATE OR REPLACE FUNCTION get_usuario()
	RETURNS SETOF usuario AS $$
	BEGIN 
		RETURN QUERY SELECT * FROM usuario
		RETURN;
	END;
	$$ LANGUAGE 'plpgsql';

SELECT * FROM get_usuario();

CREATE TYPE usuario_aluno AS (nome VARCHAR, data_nascimento DATE, desempenho VARCHAR, media NUMERIC, serie INTEGER);

CREATE OR REPLACE FUNCTION get_desempenho()
	RETURNS SETOF usuario_aluno AS $$ 
	DECLARE
		linha_desempenho usuario_aluno%ROWTYPE;
		r_desempenho RECORD; 
	BEGIN 
		FOR r_desempenho IN SELECT * FROM usuario AS usr INNER JOIN aluno AS al ON usr.codigo_usuario = al.codigo_usuario 
		LOOP 
			linha_desempenho.nome = r_desempenho.nome;
			linha_desempenho.data_nascimento = r_desempenho.data_nascimento;
			linha_desempenho.desempenho = r_desempenho.desempenho; 
			linha_desempenho.media = r_desempenho.media; 
			linha_desempenho.serie = r_desempenho.serie;
		RETURN NEXT linha_desempenho; 
		END LOOP; 
		RETURN;		 
	END;
	$$ LANGUAGE 'plpgsql';

SELECT * FROM get_desempenho()

CREATE OR REPLACE FUNCTION trg_addUsuario()
	RETURNS TRIGGER AS $$ 
	DECLARE registro VARCHAR;
	
	BEGIN 
		SELECT nome INTO registro FROM usuario WHERE nome = NEW.nome; 
		IF registro = NEW.nome THEN 
		RAISE EXCEPTION 'USUÁRIO JÁ CADASTRADO!';
		END IF;
		RETURN NEW;
	END;
	$$ LANGUAGE 'plpgsql';

CREATE TRIGGER tr_addUsuario
	BEFORE INSERT ON usuario
	FOR EACH ROW 
	EXECUTE PROCEDURE trg_addUsuario();

INSERT INTO usuario(nome, data_nascimento) VALUES ('Paulo', '1990-12-12')
INSERT INTO usuario(nome, data_nascimento) VALUES ('Berenice', '1990-12-12')

CREATE OR REPLACE FUNCTION trg_updateAluno()
	RETURNS TRIGGER AS $$
	DECLARE registro INTEGER;	
	
	BEGIN
		SELECT codigo_aluno INTO registro FROM aluno WHERE codigo_aluno = NEW.codigo_aluno;
		IF registro = NEW.codigo_aluno THEN
		RAISE EXCEPTION 'ALUNO JÁ ATUALIZADO!';
		END IF;
		RETURN NEW;
	END;
	$$ LANGUAGE 'plpgsql';
	
CREATE TRIGGER tr_updateAluno
	BEFORE UPDATE ON aluno
	FOR EACH ROW 
	EXECUTE PROCEDURE trg_updateAluno();
	
UPDATE aluno SET codigo_aluno = 1 WHERE codigo_aluno = 3;