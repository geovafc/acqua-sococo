package br.com.acqua.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Movimentacao.class)
public abstract class Movimentacao_ {

	public static volatile SingularAttribute<Movimentacao, String> observacao;
	public static volatile SingularAttribute<Movimentacao, String> situacao;
	public static volatile SingularAttribute<Movimentacao, Produto> produto;
	public static volatile SingularAttribute<Movimentacao, String> lote;
	public static volatile SingularAttribute<Movimentacao, Usuario> usuario;
	public static volatile SingularAttribute<Movimentacao, Long> id;
	public static volatile SingularAttribute<Movimentacao, AvatarProd> avatar;
	public static volatile SingularAttribute<Movimentacao, String> notaFiscal;
	public static volatile SingularAttribute<Movimentacao, Date> dataHora;

}

