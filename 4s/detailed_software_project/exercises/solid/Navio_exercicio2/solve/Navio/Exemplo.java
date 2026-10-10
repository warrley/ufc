Exemplo

Bazuca bazuca  = new Bazuca();
Navio navio = new Navio(bazuca);
navio.atacar();

BolaDeFerro bolaDeFerro = new BolaDeFerro();
Canhao canhao = new Canhao(bolaDeFerro);
navio.setArma(canhao);
navio.atacar();

PatoDeBorracha patoDeBorracha = new PatoDeBorracha();
canhao.setMunicao(patoDeBorracha);
navio.atacar();