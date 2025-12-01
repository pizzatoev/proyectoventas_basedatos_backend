# Configuración de Variables de Entorno en Render

## Variables Requeridas

Para que la aplicación funcione correctamente en Render, debes configurar las siguientes variables de entorno en el panel de Render:

### 1. JWT_SECRET (OBLIGATORIA)
- **Nombre**: `JWT_SECRET`
- **Valor**: Una clave secreta en Base64 (mínimo 32 bytes = 256 bits)
- **Cómo generarla**: 
  ```bash
  openssl rand -base64 64
  ```
  O en PowerShell:
  ```powershell
  [Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Minimum 0 -Maximum 256 }))
  ```
- **Ejemplo**: `aBcDeFgHiJkLmNoPqRsTuVwXyZ1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+/==`

### 2. Base de Datos PostgreSQL (OBLIGATORIAS)
- **SPRING_DATASOURCE_URL**: URL completa de tu base de datos PostgreSQL en Render
  - Ejemplo: `jdbc:postgresql://dpg-xxxxx-a.oregon-postgres.render.com:5432/salesmasterdb`
- **SPRING_DATASOURCE_USERNAME**: Usuario de PostgreSQL
- **SPRING_DATASOURCE_PASSWORD**: Contraseña de PostgreSQL

### 3. Opcionales
- **SPRING_JPA_SHOW_SQL**: `false` (recomendado para producción)
- **PORT**: Render lo inyecta automáticamente, no necesitas configurarlo

## Pasos para Configurar en Render

1. Ve a tu servicio en Render Dashboard
2. Click en "Environment" en el menú lateral
3. Agrega cada variable de entorno:
   - Click en "Add Environment Variable"
   - Ingresa el nombre y valor
   - Guarda los cambios
4. Render reiniciará automáticamente el servicio

## Verificación

Después de configurar las variables, verifica que el servicio se inicie correctamente. Si ves el mensaje:
```
🔑 JWT_SECRET cargada correctamente (512 bits)
```
Significa que todo está configurado correctamente.

## Nota Importante

⚠️ **NUNCA** subas el archivo `.env` con `JWT_SECRET` a GitHub. Las variables de entorno deben configurarse solo en Render.

