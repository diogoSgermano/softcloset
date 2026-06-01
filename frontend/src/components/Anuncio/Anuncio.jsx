import styles from './Anuncio.module.css'

export default function Anuncio({categoria, titulo, preco, imagem}) {
  return (
    <div className={styles.anuncio}>
      <div className={styles.imagemWrapper}>
        <img src={imagem} className={styles.imagem} alt={titulo} />
        <span
          className={`${styles.tag} ${categoria === 'Novo' ? styles.novo : categoria === 'Destaque' ? styles.destaque : ''}`}
        >
          {categoria}
        </span>
      </div>
      <div className={styles.info}>
        <h2 className={styles.titulo}>{titulo}</h2>
        <p className={styles.preco}>{preco}</p>
      </div>
    </div>
  );
}