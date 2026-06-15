import React from 'react';
import useProdutos from '../../hooks/useProdutos';
import styles from './Admin.module.css';
import LinkButton from '../../components/LinkButton/LinkButton.jsx';

export default function Admin() {
  const { produtos, loading, error, deleteProduto } = useProdutos();
  const [deleteMessage, setDeleteMessage] = React.useState('');

  const handleDelete = async (id, nome) => {
    if (window.confirm(`Tem certeza que deseja deletar o produto "${nome}"?`)) {
      const result = await deleteProduto(id);
      if (result.success) {
        setDeleteMessage(`✓ ${nome} foi deletado com sucesso!`);
        setTimeout(() => setDeleteMessage(''), 3000);
      } else {
        setDeleteMessage(`✗ Erro ao deletar: ${result.message}`);
      }
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.headerActions}>
        <LinkButton to="/" variant="primary" className={styles.backBtn}>
          ← Voltar
        </LinkButton>
        <h1>Painel de Administração</h1>
      </div>
      <h2>Gerenciar Produtos</h2>

      {deleteMessage && (
        <div className={styles.message}>
          {deleteMessage}
        </div>
      )}

      {loading && <p className={styles.status}>Carregando produtos...</p>}
      {error && <p className={styles.statusError}>Erro ao carregar: {error}</p>}

      <div className={styles.tablesContainer}>
        <table className={styles.produtosTable}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Foto</th>
              <th>Nome</th>
              <th>Marca</th>
              <th>Preço</th>
              <th>Classificação</th>
              <th>Ação</th>
            </tr>
          </thead>
          <tbody>
            {produtos.map((produto) => (
              <tr key={produto.idProduto}>
                <td>{produto.idProduto}</td>
                <td>
                  {produto.imagemUrl ? (
                    <img
                      src={produto.imagemUrl}
                      alt={produto.nome}
                      className={styles.thumb}
                      loading="lazy"
                      width={40}
                      height={40}
                    />
                  ) : (
                    <span className={styles.noThumb}>—</span>
                  )}
                </td>
                <td>{produto.nome}</td>
                <td>{produto.marca}</td>
                <td>R$ {produto.preco?.toFixed(2)}</td>
                <td>{produto.classificacao}</td>
                <td>
                  <button
                    className={styles.deleteBtn}
                    onClick={() => handleDelete(produto.idProduto, produto.nome)}
                  >
                    Deletar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {produtos.length === 0 && !loading && (
        <p className={styles.emptyMessage}>Nenhum produto encontrado.</p>
      )}
    </div>
  );
}
