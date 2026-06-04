import React, { createContext, useEffect, useState } from 'react';
import { fetchProdutos } from '../services/produtoService';

export const ProdutoContext = createContext({
  produtos: [],
  loading: false,
  error: null,
  reload: () => {},
});

export function ProdutoProvider({ children }) {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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

  useEffect(() => {
    loadProdutos();
  }, []);

  return (
    <ProdutoContext.Provider value={{ produtos, loading, error, reload: loadProdutos }}>
      {children}
    </ProdutoContext.Provider>
  );
}
