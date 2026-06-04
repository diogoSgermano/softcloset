import { useContext } from 'react';
import { ProdutoContext } from '../contexts/ProdutoContext';

export default function useProdutos() {
  const context = useContext(ProdutoContext);
  if (!context) {
    throw new Error('useProdutos deve ser usado dentro de um ProdutoProvider');
  }
  return context;
}
