package br.com.acqua.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produto.class)
public abstract class Produto_ {

	public static volatile ListAttribute<Produto, Movimentacao> movimentacoes;
	public static volatile SingularAttribute<Produto, String> imagemContentType;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, Long> id;
	public static volatile SingularAttribute<Produto, String> codigoDeBarras;
	public static volatile SingularAttribute<Produto, AvatarProd> avatar;
	public static volatile SingularAttribute<Produto, Date> dataCadastro;
	public static volatile SingularAttribute<Produto, String> descricao;

}

