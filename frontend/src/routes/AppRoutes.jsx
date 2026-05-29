import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home/Home';
import Masculino from '../pages/Masculino/Masculino';
import Feminino from '../pages/Feminino/Feminino';
import Ofertas from '../pages/Ofertas/Ofertas.jsx';
import Lancamento from '../pages/Lancamento/Lancamento.jsx'


export default function AppRoutes() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/masculino" element={<Masculino />} />
        <Route path="/feminino" element={<Feminino />} />
        <Route path="/ofertas" element={<Ofertas />} />
        <Route path="/lancamento" element={<Lancamento />} />
        
      </Routes>
    </Router>
  );
}