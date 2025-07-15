
```
Sistema Gestion Efrain Backend
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ README.md
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ GestionRemodelacion
   │  │        └─ gestion
   │  │           ├─ cliente
   │  │           │  ├─ controller
   │  │           │  │  └─ ClienteController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ request
   │  │           │  │  │  └─ ClienteRequest.java
   │  │           │  │  └─ response
   │  │           │  │     └─ ClienteResponse.java
   │  │           │  ├─ model
   │  │           │  │  └─ Cliente.java
   │  │           │  ├─ repository
   │  │           │  │  └─ ClienteRepository.java
   │  │           │  └─ service
   │  │           │     └─ ClienteService.java
   │  │           ├─ config
   │  │           │  ├─ JwtProperties.java
   │  │           │  └─ SecurityConfig.java
   │  │           ├─ controller
   │  │           │  ├─ auth
   │  │           │  │  └─ AuthController.java
   │  │           │  ├─ dashboard
   │  │           │  │  └─ DashboardController.java
   │  │           │  ├─ role
   │  │           │  │  └─ RoleController.java
   │  │           │  └─ user
   │  │           │     └─ UserController.java
   │  │           ├─ dto
   │  │           │  ├─ request
   │  │           │  │  ├─ LoginRequest.java
   │  │           │  │  ├─ RefreshTokenRequest.java
   │  │           │  │  └─ SignupRequest.java
   │  │           │  └─ response
   │  │           │     ├─ ApiResponse.java
   │  │           │     ├─ AuthResponse.java
   │  │           │     ├─ DashboardSummaryResponse.java
   │  │           │     └─ JwtResponse.java
   │  │           ├─ empleado
   │  │           │  ├─ controller
   │  │           │  │  └─ EmpleadoController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ request
   │  │           │  │  │  └─ EmpleadoRequest.java
   │  │           │  │  └─ response
   │  │           │  │     └─ EmpleadoResponse.java
   │  │           │  ├─ model
   │  │           │  │  └─ Empleado.java
   │  │           │  ├─ repository
   │  │           │  │  └─ EmpleadoRepository.java
   │  │           │  └─ service
   │  │           │     └─ EmpleadoService.java
   │  │           ├─ GestionApplication.java
   │  │           ├─ horastrabajadas
   │  │           │  ├─ controller
   │  │           │  │  └─ HorasTrabajadasController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ request
   │  │           │  │  │  └─ HorasTrabajadasRequest.java
   │  │           │  │  └─ response
   │  │           │  │     └─ HorasTrabajadasResponse.java
   │  │           │  ├─ model
   │  │           │  │  └─ HorasTrabajadas.java
   │  │           │  ├─ repository
   │  │           │  │  └─ HorasTrabajadasRepository.java
   │  │           │  └─ service
   │  │           │     └─ HorasTrabajadasService.java
   │  │           ├─ model
   │  │           │  ├─ Permission.java
   │  │           │  ├─ RefreshToken.java
   │  │           │  ├─ Role.java
   │  │           │  └─ User.java
   │  │           ├─ proyecto
   │  │           │  ├─ controller
   │  │           │  │  └─ ProyectoController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ request
   │  │           │  │  │  └─ ProyectoRequest.java
   │  │           │  │  └─ response
   │  │           │  │     └─ ProyectoResponse.java
   │  │           │  ├─ model
   │  │           │  │  └─ Proyecto.java
   │  │           │  ├─ repository
   │  │           │  │  └─ ProyectoRepository.java
   │  │           │  └─ service
   │  │           │     └─ ProyectoService.java
   │  │           ├─ repository
   │  │           │  ├─ PermissionRepository.java
   │  │           │  ├─ RefreshTokenRepository.java
   │  │           │  ├─ RoleRepository.java
   │  │           │  └─ UserRepository.java
   │  │           ├─ security
   │  │           │  ├─ exception
   │  │           │  │  ├─ JwtAuthenticationEntryPoint.java
   │  │           │  │  └─ TokenRefreshException.java
   │  │           │  └─ jwt
   │  │           │     ├─ JwtAuthFilter.java
   │  │           │     ├─ JwtUtils.java
   │  │           │     └─ RateLimitFilter.java
   │  │           └─ service
   │  │              ├─ auth
   │  │              │  ├─ AuthService.java
   │  │              │  ├─ JwtService.java
   │  │              │  ├─ RefreshTokenService.java
   │  │              │  └─ TokenBlacklistService.java
   │  │              ├─ dashboard
   │  │              │  └─ DashboardService.java
   │  │              ├─ impl
   │  │              │  ├─ UserDetailsImpl.java
   │  │              │  └─ UserDetailsServiceImpl.java
   │  │              ├─ role
   │  │              │  └─ RoleService.java
   │  │              └─ user
   │  │                 └─ UserService.java
   │  └─ resources
   │     └─ application.properties
   └─ test
      └─ java
         └─ com
            └─ GestionRemodelacion
               └─ gestion
                  └─ GestionApplicationTests.java

```