import React from 'react';
import styles from './Destaques.module.css';
import Anuncio from '../Anuncio/Anuncio';
import useProdutos from '../../hooks/useProdutos';
import vestido_vermelho from '../../assets/images/vestido-vermelho.jpg';

const produtoExemplo = {
  idProduto: 0,
  nome: 'Vestido Vermelho',
  classificacao: 'Novo',
  preco: 199.9,
  imagemUrl: vestido_vermelho,
};

function formatPreco(preco) {
  if (typeof preco === 'number') {
    return `R$ ${preco.toFixed(2).replace('.', ',')}`;
  }
  return preco || 'R$ --';
}

export default function Destaques() {
  const { produtos, loading, error } = useProdutos();
  // Mostrar todos os produtos retornados pela API; usar exemplo quando vazio
  const produtosExibidos = produtos.length > 0 ? produtos : [produtoExemplo];

  return (
    <section>
      <h1 className={styles.title}>Produtos em destaque</h1>
      {loading && <p className={styles.status}>Carregando produtos...</p>}
      {error && <p className={styles.statusError}>Erro ao carregar: {error}</p>}
      <div className={styles.destaques}>
        {produtosExibidos.map((produto, index) => (
          <Anuncio
            key={`destaque-${produto.idProduto ?? index}`}
            classificacao={produto.classificacao || produto.categoria || 'Normal'}
            titulo={produto.nome}
            preco={formatPreco(produto.preco)}
            imagem={produto.imagemUrl || vestido_vermelho}
          />
        ))}
      </div>
    </section>
  );
}

