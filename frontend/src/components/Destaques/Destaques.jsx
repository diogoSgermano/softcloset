import React from 'react'
import styles from './Destaques.module.css'
import Anuncio from '../Anuncio/Anuncio'
import vestido_vermelho from '../../assets/images/vestido-vermelho.jpg'
export default function Destaques(){
  return (
    <section>
        <h1 className={styles.title}>Produtos em destaque</h1>
        <div className={styles.destaques}>
            <Anuncio
            categoria="Novo"
             titulo="Vestido Vermelho"
             preco="R$ 199,90"
             imagem={vestido_vermelho}
            />
            <Anuncio
            categoria="Novo"
             titulo="Vestido Vermelho"
             preco="R$ 199,90"
             imagem={vestido_vermelho}
            />
            <Anuncio
            categoria="Novo"
             titulo="Vestido Vermelho"
             preco="R$ 199,90"
             imagem={vestido_vermelho}
            />
            <Anuncio
            categoria="Novo"
             titulo="Vestido Vermelho"
             preco="R$ 199,90"
             imagem={vestido_vermelho}
            />

        </div>
    </section>
  )
}

