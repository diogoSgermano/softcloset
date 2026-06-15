const API_BASE_URL = 'http://localhost:8080/api/produtos';

export async function fetchProdutos() {
  const response = await fetch(API_BASE_URL);
  if (!response.ok) {
    throw new Error(`Falha ao carregar produtos: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

export async function fetchProdutoById(id) {
  const response = await fetch(`${API_BASE_URL}/${id}`);
  if (!response.ok) {
    throw new Error(`Produto não encontrado: ${id}`);
  }
  return response.json();
}

export async function fetchProdutosPorPesquisa(query) {
  const response = await fetch(`${API_BASE_URL}/search?q=${encodeURIComponent(query)}`);
  if (!response.ok) {
    throw new Error(`Erro na pesquisa: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

export async function fetchProdutosPorClassificacao(classificacao) {
  const response = await fetch(`${API_BASE_URL}/classificacao?classificacao=${encodeURIComponent(classificacao)}`);
  if (!response.ok) {
    throw new Error(`Erro ao filtrar por classificação: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

export async function deleteProduto(id) {
  const response = await fetch(`${API_BASE_URL}/${id}`, {
    method: 'DELETE'
  });

  if (!response.ok) {
    throw new Error(`Erro ao deletar o produto ID ${id}: ${response.status} ${response.statusText}`);
  }
  return { message: 'Produto deletado com sucesso' };
}

