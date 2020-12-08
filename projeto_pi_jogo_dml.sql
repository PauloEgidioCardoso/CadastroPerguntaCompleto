INSERT INTO turma(turno,numero_turma) VALUES 
			('Matutino','101'),('Vespertino','102'),('Maturino','103')
SELECT * FROM turma

INSERT INTO usuario(nome,data_nascimento) VALUES
			('Marcos','1988-12-09'),('Thiago','1985-06-01'),('Paulo','1991-06-10'),
			('Gabriel','1994-05-12'),('Carlos Eduardo','1994-10-08'),('Ciclano','2000-06-10')
SELECT * FROM usuario

INSERT INTO aluno(codigo_usuario,codigo_turma,desempenho,media,serie) VALUES 
			(1,1,'Baixo',7.5,1), (2,1,'Médio',7.5,1), (3,1,'Baixo',7.5,1)
SELECT * FROM aluno

INSERT INTO professor(codigo_usuario,cpf) VALUES
			(4,'022.256.256-56'), (5,'256.569.587-89'), (6,'123.456.789-12')
SELECT * FROM professor

INSERT INTO endereco(codigo_usuario,estado,cidade,bairo,rua,numero,complemento,cep) VALUES
			(1,'Santa Catarina','Florianópolis','Ingleses','Rua Morro das Feiticeiras','495','108B','88058-583'),
			(2,'Santa Catarina','Florianópolis','Coqueiros','Rua Paula Ramos','1134','309B','88080-401'),
			(3,'Santa Catarina','Florianópolis','Lagoa da Conceição','Avenida das Rendeiras','1135','Sem Complemento','88856-454'),
			(4,'Santa Catarina','Florianópolis','Ingleses','Rua Morro das Feiticeiras','495','108B','88058-583'),
			(5,'Santa Catarina','Florianópolis','Coqueiros','Rua Paula Ramos','1134','309B','88080-401'),
			(6,'Santa Catarina','Florianópolis','Lagoa da Conceição','Avenida das Rendeiras','1135','Sem Complemento','88856-454')
SELECT * FROM endereco

SELECT codigo_aluno, codigo_usuario, desempenho FROM aluno UNION SELECT  
       codigo_professor, codigo_usuario, cpf FROM professor

SELECT codigo_usuario FROM usuario EXCEPT SELECT  codigo_usuario FROM professor

SELECT u.codigo_usuario, a.codigo_aluno, u.nome, a.desempenho, a.serie FROM usuario AS u 
       CROSS JOIN aluno AS a WHERE u.codigo_usuario=a.codigo_aluno 

SELECT u.codigo_usuario, a.codigo_aluno, u.nome, a.desempenho, a.serie, e.estado, e.cep
       FROM usuario AS u CROSS JOIN aluno AS a , endereco AS e WHERE u.codigo_usuario=a.codigo_aluno

SELECT u.codigo_usuario, a.codigo_aluno, u.nome, a.desempenho, a.serie FROM usuario AS u 
       CROSS JOIN aluno AS a WHERE u.codigo_usuario=a.codigo_aluno AND a.desempenho='Médio'

SELECT codigo_aluno, desempenho, media FROM aluno 
	   ORDER BY codigo_aluno LIMIT 	2
	   
SELECT codigo_professor, codigo_usuario, cpf FROM professor 
	   ORDER BY codigo_professor LIMIT 3
	   
SELECT codigo_usuario, nome, data_nascimento FROM usuario
	   ORDER BY codigo_usuario ASC LIMIT 3	

SELECT codigo_usuario, nome, data_nascimento FROM usuario
	   ORDER BY codigo_usuario DESC LIMIT 3
	   
INSERT INTO usuario(nome,data_nascimento) VALUES
			('Paula','1988-12-09'),('Joana','1985-06-01'),('Roberta','1991-06-10'),
			('Juju','1994-05-12'),('Sula','1994-10-08'),('Luana','2000-06-10'),
			('Carolina','1988-12-09'),('Pedro','1985-06-01'),('João','1991-06-10'),
			('Pauladinha','1994-05-12'),('Eduardo','1994-10-08'),('Cabeça','2000-06-10')
			
SELECT * FROM usuario

UPDATE usuario SET nome='Roberta' WHERE codigo_usuario=18
UPDATE usuario SET nome='Eduardo', data_nascimento='2000-05-24' WHERE codigo_usuario=15
UPDATE usuario SET data_nascimento='1998-03-15' WHERE codigo_usuario=11				   
UPDATE usuario SET data_nascimento='1985-05-12' WHERE codigo_usuario=13	

DELETE FROM usuario WHERE nome='Pauladinha'
DELETE FROM usuario WHERE nome='Cabeça'
DELETE FROM usuario WHERE nome='Juju'
DELETE FROM usuario WHERE nome='Sula'
			
			
INSERT INTO indicacaodificuldade(serie) VALUES ('1 Série'), ('2 Série'), ('3 Série'), ('4 Série'), ('5 Série')			
	   
	   
	   
