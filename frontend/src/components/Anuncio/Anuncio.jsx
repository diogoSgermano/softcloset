import styles from './Anuncio.module.css'

export default function Anuncio({ classificacao, titulo, preco, imagem }) {
  const classificacaoNormalizada = classificacao?.toString().toLowerCase();
  const showTag = classificacaoNormalizada === 'novo' || classificacaoNormalizada === 'destaque';
  const classificacaoClasse = classificacaoNormalizada === 'novo'
    ? styles.novo
    : classificacaoNormalizada === 'destaque'
    ? styles.destaque
    : styles.normal;
  const label = classificacaoNormalizada === 'novo'
    ? 'Novo'
    : classificacaoNormalizada === 'destaque'
    ? 'Destaque'
    : classificacao;

  return (
    <div className={styles.anuncio}>
      <div className={styles.imagemWrapper}>
        <img src={imagem} className={styles.imagem} alt={titulo} />
        {showTag && (
          <span className={`${styles.classificacao} ${classificacaoClasse}`}>
            {label}
          </span>
        )}
      </div>
      <div className={styles.info}>
        <h2 className={styles.titulo}>{titulo}</h2>
        <p className={styles.preco}>{preco}</p>
      </div>
    </div>
  );
}
