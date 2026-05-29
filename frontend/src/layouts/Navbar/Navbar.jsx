import React, { useState } from 'react';
import styles from './Navbar.module.css';
import logo from '../../assets/images/logo-softcloset-v2.png';
import LinkButton from '../../components/LinkButton/LinkButton.jsx';
import lupa from '../../assets/images/search.png';

export default function Navbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isSearchOpen, setIsSearchOpen] = useState(false);

  function toggleMenu() {
    setIsMenuOpen((prev) => !prev);
  }

  function toggleSearch() {
    setIsSearchOpen((prev) => !prev);
  }

  return (
    <nav>
      <div className={styles.navbar}>
        <div className={styles.logoContainer}>
          <LinkButton to="/" variant="transparent">
            <img className={styles.logoNavbar} src={logo} alt="logo SoftCloset" />
          </LinkButton>
        </div>

        <div className={styles.desktopNav}>
          <ul className={styles.navLinks}>
            <li>
              <LinkButton to="/masculino" variant="transparent">
                Masculino
              </LinkButton>
            </li>
            <li>
              <LinkButton to="/feminino" variant="transparent">
                Feminino
              </LinkButton>
            </li>
            <li>
              <LinkButton to="/ofertas" variant="transparent">
                Ofertas
              </LinkButton>
            </li>
            <li>
              <LinkButton to="/lancamento" variant="transparent">
                Lançamentos
              </LinkButton>
            </li>
          </ul>
          <div className={`${styles.barra_pesquisa} ${isSearchOpen ? styles.searchOpen : ''}`}>
            <button
              type="button"
              className={styles.searchButton}
              aria-label="Abrir busca"
              onClick={toggleSearch}
            >
              <img src={lupa} className={styles.lupa} alt="lupa de pesquisa" />
            </button>
            <input
              className={styles.input}
              placeholder="Buscar produtos..."
              aria-label="Buscar produtos"
              onFocus={() => setIsSearchOpen(true)}
              onBlur={() => setIsSearchOpen(false)}
            />
          </div>
        </div>

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
          <div className={`${styles.barra_pesquisa} ${isSearchOpen ? styles.searchOpen : ''}`}>
            <button
              type="button"
              className={styles.searchButton}
              aria-label="Abrir busca"
              onClick={toggleSearch}
            >
              <img src={lupa} className={styles.lupa} alt="lupa de pesquisa" />
            </button>
            <input
              className={styles.input}
              placeholder="Buscar produtos..."
              aria-label="Buscar produtos"
              onFocus={() => setIsSearchOpen(true)}
              onBlur={() => setIsSearchOpen(false)}
            />
          </div>
        </div>
      </div>

      {isMenuOpen && (
        <div className={styles.mobileMenu}>
          <LinkButton to="/masculino" variant="transparent" className={styles.mobileMenuLink}>
            Masculino
          </LinkButton>
          <LinkButton to="/feminino" variant="transparent" className={styles.mobileMenuLink}>
            Feminino
          </LinkButton>
          <LinkButton to="/ofertas" variant="transparent" className={styles.mobileMenuLink}>
            Ofertas
          </LinkButton>
          <LinkButton to="/lancamento" variant="transparent" className={styles.mobileMenuLink}>
            Lançamentos
          </LinkButton>
        </div>
      )}
    </nav>
  );
}