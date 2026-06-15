import React, { useState, useContext } from 'react';
import styles from './Navbar.module.css';
import logo from '../../assets/images/logo-softcloset-v2.png';
import LinkButton from '../../components/LinkButton/LinkButton.jsx';
import lupa from '../../assets/images/search.png';
import { ProdutoContext } from '../../contexts/ProdutoContext';

export default function Navbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [termoBusca, setTermoBusca] = useState('');
  const { pesquisar, filtrarPorClassificacao, reload } = useContext(ProdutoContext);

  function toggleMenu() {
    setIsMenuOpen((prev) => !prev);
  }

  function toggleSearch() {
    setIsSearchOpen((prev) => !prev);
  }

  function handlePesquisa(e) {
  if (e.key === 'Enter') {
    pesquisar(termoBusca);
    rolarParaProdutos();
  }
}

function handleClickLupa(e) {
    e.preventDefault();  
  if (termoBusca.trim()) {
    pesquisar(termoBusca);
    rolarParaProdutos();
  } else {
    toggleSearch();
  }
}

function rolarParaProdutos() {
  const secao = document.querySelector('#destaques'); 
  if (secao) {
    secao.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}

function filtrarPorDestaques() {
  filtrarPorClassificacao('DESTAQUE');
  rolarParaProdutos();
}

function filtrarPorLancamentos() {
  filtrarPorClassificacao('NOVO');
  rolarParaProdutos();
}

function mostrarTodosProdutos() {
  reload();
  rolarParaProdutos();
}

  return (
    <header>
      <nav>
        <div className={styles.navbar}>
          <section>
            <div className={styles.logoContainer}>
              <LinkButton to="/" variant="transparent">
                <img className={styles.logoNavbar} src={logo} alt="logo SoftCloset" />
              </LinkButton>
            </div>
          </section>

          <section>
            <div className={styles.navLinksContainer}>
              <ul>
                <li>
                  <LinkButton to="/" onClick={mostrarTodosProdutos} variant="primary">
                    Todos os produtos
                  </LinkButton>
                </li>
                <li>
                  <LinkButton to="/admin" variant="primary">
                    Gerenciar produtos
                  </LinkButton>
                </li>
                <li>
                  <LinkButton to="/" onClick={filtrarPorDestaques} variant="primary">
                    Destaques da semana
                  </LinkButton>
                </li>
                <li>
                  <LinkButton to="/" onClick={filtrarPorLancamentos} variant="primary">
                    Lançamentos
                  </LinkButton>
                </li>
              </ul>
            </div>
          </section>

          <section>
            <div className={`${styles.barra_pesquisa} ${isSearchOpen ? styles.searchOpen : ''}`}>
              <button
                type="button"
                className={styles.searchButton}
                aria-label="Buscar"
                onMouseDown={handleClickLupa}
              >
                <img src={lupa} className={styles.lupa} alt="lupa de pesquisa" />
              </button>
              <input
                className={styles.input}
                placeholder="Buscar produtos..."
                aria-label="Buscar produtos"
                value={termoBusca}
                onChange={(e) => setTermoBusca(e.target.value)}
                onKeyDown={handlePesquisa}
                onFocus={() => setIsSearchOpen(true)}
                onBlur={() => setIsSearchOpen(false)}
              />
            </div>
          </section>

          <section>
            <div className={styles.mobileNav}>
              <button
                type="button"
                className={styles.hamburger}
                aria-label="Abrir menu"
                aria-expanded={isMenuOpen}
                onClick={toggleMenu}
              >
                ☰
              </button>
            </div>
          </section>
        </div>

        <div className={`${styles.mobileMenu} ${isMenuOpen ? styles.open : ''}`}>
          <LinkButton to="/" onClick={mostrarTodosProdutos} variant="primaryMobile">
            Todos os produtos
          </LinkButton>
          <LinkButton to="/admin" variant="primaryMobile">
            Gerenciar produtos
          </LinkButton>
          <LinkButton to="/" onClick={filtrarPorDestaques} variant="primaryMobile">
            Destaques da semana
          </LinkButton>
          <LinkButton to="/" onClick={filtrarPorLancamentos} variant="primaryMobile">
            Lançamentos
          </LinkButton>
        </div>
      </nav>
    </header>
  );
}