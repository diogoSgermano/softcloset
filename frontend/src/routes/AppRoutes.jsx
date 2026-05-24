import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home/Home';
import Masculino from '../pages/Masculino/Masculino';
import Feminino from '../pages/Feminino/Feminino';

export default function AppRoutes() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/masculino" element={<Masculino />} />
        <Route path="/feminino" element={<Feminino />} />
      </Routes>
    </Router>
  );
}