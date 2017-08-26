insert into permissao (id, nome) values 
	(1, 'ROLE_ADMIN')
	, (2, 'ROLE_USER')
;
	
insert into usuario (id,codigo, nome,sobrenome, username, password, enabled, data_cadastro) values 
	(1,'001', 'Jairo','Nascimento', 'jairo.sousa', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true, '2017-08-19')
	, (2,'002', 'Geovane','Freitas', 'geovane.freitas', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true, '2017-08-19')
	, (3,'003', 'Rayan','Teixeira', 'rayan.teixeira', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true, '2017-08-19')
	, (4,'004', 'User','Default', 'user', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true, '2017-08-19')
;
	
insert into usuario_permissoes (usuario_id, permissao_id) values 
	  (1, 1)
	, (1, 2)
	, (2, 1)
	, (2, 2)
	, (3, 1)
	, (3, 2)
	, (4,2)
;

insert into avatares(id, avatar, tipo, titulo) values 
(1, 'sblnsdkvbsjvnjdbhfysvzdgwhedjbhxbhb@cjjf', 'image/png', 'default.png'),
(2, 'sblnsdkvbsjvnjdbhfysvzdgwhedjbhxbhb@cjjf', 'image/png', 'default.png'),
(3, 'sblnsdkvbsjvnjdbhfysvzdgwhedjbhxbhb@cjjf', 'image/png', 'default.png'),
(4, 'sblnsdkvbsjvnjdbhfysvzdgwhedjbhxbhb@cjjf', 'image/png', 'default.png');

insert into produto (id, nome, codigo_de_barras, descricao, data_cadastro, avatar_id, imagem_content_type) values
(1, 'Agua de Cocô 200ml', '123', 'Caixa Tetra Pack 200ml', '2017-08-19', 1, null ),
(2, 'Agua de Cocô 500ml', '1234', 'Caixa Tetra Pack 500ml', '2017-08-19', 2, null ),
(3, 'Cocô Ralado 150g', '12345', 'Pacote 150g', '2017-08-19', 3, null ),
(4, 'Manteiga Sococõ 1kg', '123456', 'Pacote 1kg', '2017-08-19', 4, null );

insert into movimentacao (id, data_hora, observacao, situacao, lote, produto_id, usuario_id) values
(1, '2017-08-19', 'Sem observação', 'Normal', '01', 1, 2),
(2, '2017-08-19', 'Sem observação', 'Normal', '01', 2, 2),
(3, '2017-08-19', 'Sem observação', 'Normal', '01', 3, 2),
(4, '2017-08-19', 'Sem observação', 'Normal', '01', 4, 2);


