  import React, { createContext, useEffect, useState } from 'react';
  import { fetchProdutos, fetchProdutosPorPesquisa, fetchProdutosPorClassificacao, deleteProduto as deleteProdutoService } from '../services/produtoService';

  export const ProdutoContext = createContext({
    produtos: [],
    loading: false,
    error: null,
    reload: () => {},
    pesquisar: () => {},  
    query: '',
    filtrarPorClassificacao: () => {},
    deleteProduto: () => {},            
  });

  export function ProdutoProvider({ children }) {
    const [produtos, setProdutos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [query, setQuery] = useState('');  
    const [classificacao,setClassificacao] = useState('');

    const filtrarPorClassificacao = async (classificacao) => {
      setClassificacao(prevClassificacao => prevClassificacao === classificacao ? '' : classificacao);
      setLoading(true);
      setError(null);
      try {
        const resultado = await fetchProdutosPorClassificacao(classificacao);
        setProdutos(resultado);
      } catch (err) {
        setError(err.message || 'Erro ao filtrar por classificação');
      } finally {
        setLoading(false);
      }
    };

    const loadProdutos = async () => {
      setLoading(true);
      setError(null);
      try {
        const produtosApi = await fetchProdutos();
        setProdutos(produtosApi);
      } catch (err) {
        setError(err.message || 'Erro ao carregar produtos');
      } finally {
        setLoading(false);
      }
    };

    const pesquisar = async (termo) => {
    setQuery(termo);

    if (!termo.trim()) {
      return loadProdutos();
    }

    
    if (termo.trim().length < 2) return;

    setLoading(true);
    setError(null);
    try {
      const resultado = await fetchProdutosPorPesquisa(termo);
      setProdutos(resultado);
    } catch (err) {
      setError(err.message || 'Erro na pesquisa');
    } finally {
      setLoading(false);
    }
  };

  const deleteProduto = async (id) => {
    setError(null);
    try {
      await deleteProdutoService(id);
      setProdutos(produtos.filter(p => p.idProduto !== id));
      return { success: true, message: 'Produto deletado com sucesso' };
    } catch (err) {
      const errorMsg = err.message || 'Erro ao deletar produto';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    }
  };

    useEffect(() => {
      loadProdutos();
    }, []);

    return (
      <ProdutoContext.Provider value={{ produtos, loading, error, reload: loadProdutos, pesquisar, query, filtrarPorClassificacao, deleteProduto }}>
        {children}
      </ProdutoContext.Provider>
    );
}