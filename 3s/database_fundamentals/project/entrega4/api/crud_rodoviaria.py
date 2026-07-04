from fastapi import APIRouter, HTTPException
from models import Rodoviaria, RodoviariaUpdate
from db import get_connection
from typing import List, Optional

router = APIRouter()

@router.post("/rodoviaria")
async def criar_rodoviaria(rod: Rodoviaria):
    conn = get_connection()
    cur = conn.cursor()

    try:
        cur.execute(
            "INSERT INTO rodoviaria (nome, cep, rua, numero, bairro, id_cidade) VALUES(%s, %s, %s, %s, %s, %s)",
            (rod.nome, rod.cep, rod.rua, rod.numero, rod.bairro, rod.id_cidade)
        )
        print("foi")

        conn.commit()
    except Exception as e:
        conn.rollback()
        raise HTTPException(400, f"Erro ao cadastrar uma rodoviaria {e}")
    finally:
        cur.close()
        conn.close()
    return {"msg": "Rodoviaria criada com sucesso"}

@router.get("/rodoviarias", response_model=List[Rodoviaria])
async def listar_rodoviarias():
    conn = get_connection()
    cur = conn.cursor()
    
    cur.execute("SELECT id_rodoviaria, nome, cep, rua, numero, bairro, id_cidade from rodoviaria")
    rows = cur.fetchall()

    cur.close()
    conn.close()

    return [
        Rodoviaria(
            id_rodoviaria=d[0],
            nome=d[1],
            cep=d[2],
            rua=d[3],
            numero=d[4],
            bairro=d[5],
            id_cidade=d[6]
        ) for d in rows 
    ]

@router.get("/rodoviaria/{id_rodoviaria}", response_model=Rodoviaria)
async def get_rodoviaria(id_rodoviaria: int):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute("SELECT id_rodoviaria, nome, cep, rua, numero, bairro, id_cidade FROM rodoviaria WHERE id_rodoviaria=%s", (id_rodoviaria,))
    row = cur.fetchone()
    cur.close()
    conn.close()
    
    if row:
        return Rodoviaria(id_rodoviaria=row[0], nome=row[1], cep=row[2], rua=row[3], numero=row[4], bairro=row[5], id_cidade=row[6])
    raise HTTPException(404, "Rodoviaria nao encontrada")


@router.delete("/rodoviaria/{id_rodoviaria}")
async def deletar_rodoviaria(id_rodoviaria: int):
    conn = get_connection()
    cur = conn.cursor()

    cur.execute("DELETE FROM rodoviaria WHERE id_rodoviaria=%s", (id_rodoviaria,))
    conn.commit()    

    cur.close()
    conn.close()

    return {"msg": "Rodoviaria deletada com sucesso"}

@router.patch("/rodoviaria/{id_rodoviaria}")
async def atualizar_rod(id_rodoviaria: int, rod: RodoviariaUpdate):
    conn = get_connection()
    cur = conn.cursor()

    cur.execute("SELECT id_rodoviaria FROM rodoviaria WHERE id_rodoviaria=%s", (id_rodoviaria,))
    if not cur.fetchone():
        cur.close()
        conn.close()
        raise HTTPException(404, "Rodoviaria nao encontrada")
    
    fields = []
    values = []
    
    for campo, valor in rod.model_dump(exclude_unset=True).items():
        fields.append(f"{campo}=%s")
        values.append(valor)

    if not fields:
        cur.close()
        conn.close()
        raise HTTPException(400, "Nenhum campo para atualizar")

    values.append(id_rodoviaria)
    
    try:
        cur.execute(f"UPDATE rodoviaria SET {', '.join(fields)} WHERE id_rodoviaria=%s", tuple(values))
        conn.commit()
    except Exception as e:
        conn.rollback()
        raise HTTPException(400, f"Erro ao atualizar {e}")
    finally:
        cur.close()
        conn.close()
    return {"msg": "Rodoviaria atualizada"}
