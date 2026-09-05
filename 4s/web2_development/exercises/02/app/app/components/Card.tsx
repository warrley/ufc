export const Card = async () => {
  const getRandomId = () => Math.floor(Math.random() * 53);

  const res = await fetch("https://thronesapi.com/api/v2/Characters/" + getRandomId());
  const character = await res.json();

  return (
    <div className="flex flex-col p-4 justify-center items-center w-sm rounded-xl border border-zinc-200 bg-white">
      <img
        src={character.imageUrl}
        alt="Name"
        className="h-md w-md rounded-lg object-cover"
      />
      <h1 className="mt-2 font-semibold text-zinc-900 text-2xl">{character.fullName}</h1>
      <p className="text-zinc-500 text-md">Título: {character.title} | Família: {character.family}</p>
    </div>
  );
};
