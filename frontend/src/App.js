import './App.css';
import AppRoutes from './routes/AppRoutes';
import { ProdutoProvider } from './contexts/ProdutoContext';

function App() {
  return (
    <ProdutoProvider>
      <div className="App">
        <AppRoutes />
      </div>
    </ProdutoProvider>
  );
}

export default App;
