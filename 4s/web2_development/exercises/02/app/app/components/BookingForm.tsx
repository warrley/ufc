"use client";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker, TimePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';

export function BookingForm() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <form>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
          <TextField
            required
            label="Nome Completo"
            name="name"
            fullWidth
          />

          <TextField
            required
            label="Telefone"
            name="phone"
            type="tel"
            fullWidth
          />

          <FormControl required fullWidth>
            <InputLabel id="service-label">Serviço</InputLabel>
            <Select
              labelId="service-label"
              name="service"
              defaultValue=""
              label="Serviço"
            >
              <MenuItem value="Corte de Cabelo">Corte de Cabelo (R$30)</MenuItem>
              <MenuItem value="Barba">Barba (R$15)</MenuItem>
              <MenuItem value="Cabelo & Barba">Cabelo & Barba (R$40)</MenuItem>
              <MenuItem value="Máquina">Corte na Máquina (R$20)</MenuItem>
            </Select>
          </FormControl>

          <DatePicker
            label="Data *"
          />

          <TimePicker
            label="Horário *"
          />

          <Button
            type="button"
            variant="contained"
            color="primary"
            size="large"
            sx={{ mt: 2 }}
          >
            Confirmar Agendamento
          </Button>
        </Box>
      </form>
    </LocalizationProvider>
  );
}
