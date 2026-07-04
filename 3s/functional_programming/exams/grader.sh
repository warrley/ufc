#!/bin/bash

echo "========================================" >results.txt
echo "       Resultados Correcao              " >>results.txt
echo "========================================" >>results.txt

for student_file in *.hs; do
  if [ "$student_file" == "grader.hs" ]; then
    continue
  fi

  echo "Corrigindo $student_file..."

  echo -e "\n\n>>> ALUNO: $student_file" >>results.txt

  cat "$student_file" grader.hs >temp_run.hs

  ghci -v0 temp_run.hs -e "runTests" >>results.txt 2>&1

  rm temp_run.hs
done

echo "Finalizado! abra 'results.txt'"
