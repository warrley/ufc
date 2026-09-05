interface Pokemon {
    id: number;
    name: string;
    sprites: {
        front_default: string;
    };
}

let id: number = 1;

const pegarPokemon = async () => {
    const res: Response = await fetch("https://pokeapi.co/api/v2/pokemon/" + id);

    const pokemon: Pokemon = await res.json();

    adicionarPokemon(pokemon);

    console.log(pokemon.sprites.front_default);

    id++;
};

document.addEventListener("click", pegarPokemon);

const adicionarPokemon = (pokemon: Pokemon): void => {
    const container = document.getElementById("pokemons");

    if (!container) return;

    const elemento: HTMLDivElement = document.createElement("div");

    elemento.classList.add("pokemon");

    if (id % 2 === 0) {
        console.log(id);
        elemento.classList.add("par");
    } else {
        elemento.classList.add("impar");
    }

    elemento.innerHTML = `
        <img
            src="${pokemon.sprites.front_default}"
            alt="${pokemon.name}"
        >

        <h2>${pokemon.name}</h2>

        <p>#${pokemon.id}</p>
    `;

    container.appendChild(elemento);
}