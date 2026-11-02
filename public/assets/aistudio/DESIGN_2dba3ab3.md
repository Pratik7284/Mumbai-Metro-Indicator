---
name: Metro Mumbai Guide
colors:
  surface: '#f8f9ff'
  surface-dim: '#cbdbf5'
  surface-bright: '#f8f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eff4ff'
  surface-container: '#e5eeff'
  surface-container-high: '#dce9ff'
  surface-container-highest: '#d3e4fe'
  on-surface: '#0b1c30'
  on-surface-variant: '#434655'
  inverse-surface: '#213145'
  inverse-on-surface: '#eaf1ff'
  outline: '#747686'
  outline-variant: '#c4c5d7'
  surface-tint: '#2151da'
  primary: '#0037b0'
  on-primary: '#ffffff'
  primary-container: '#1d4ed8'
  on-primary-container: '#cad3ff'
  inverse-primary: '#b7c4ff'
  secondary: '#565e74'
  on-secondary: '#ffffff'
  secondary-container: '#dae2fd'
  on-secondary-container: '#5c647a'
  tertiary: '#004870'
  on-tertiary: '#ffffff'
  tertiary-container: '#006194'
  on-tertiary-container: '#b2d9ff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dce1ff'
  primary-fixed-dim: '#b7c4ff'
  on-primary-fixed: '#001551'
  on-primary-fixed-variant: '#0039b5'
  secondary-fixed: '#dae2fd'
  secondary-fixed-dim: '#bec6e0'
  on-secondary-fixed: '#131b2e'
  on-secondary-fixed-variant: '#3f465c'
  tertiary-fixed: '#cce5ff'
  tertiary-fixed-dim: '#93ccff'
  on-tertiary-fixed: '#001d31'
  on-tertiary-fixed-variant: '#004b73'
  background: '#f8f9ff'
  on-background: '#0b1c30'
  surface-variant: '#d3e4fe'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 36px
    fontWeight: '700'
    lineHeight: 44px
    letterSpacing: -0.02em
  display-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
    letterSpacing: 0em
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: -0.01em
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0em
  body-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
    letterSpacing: 0em
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Inter
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 12px
    letterSpacing: 0.04em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-sm: 0.75rem
  gutter-lg: 1.5rem
  margin: 1rem
  margin-sm: 0.75rem
  margin-lg: 1.5rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system establishes a high-reliability civic transit interface engineered for rapid cognitive processing under direct sunlight, moving trains, and bustling crowd conditions. Blending the systematic utility of Material Design 3 with the refined, high-density minimalism of tier-one fintech and mobility services, the aesthetic communicates unwavering punctuality, authority, and effortless modern mobility.

The emotional core is calm efficiency and civic pride: riders should feel oriented in fractions of a second, confident in route options, and protected from commuter fatigue. Visual noise is aggressively eliminated in favor of pristine white canvases, structural typography, authentic line-color wayfinding, and tactile, elevated interactive surfaces.

## Colors

The color architecture separates navigation logic from operational wayfinding.

- **Brand & Action**: The primary anchor is `#1D4ED8` (Mumbai Metro Blue), reserved for high-intent actions, primary interactive states, and navigational confirmation. `#0F172A` grounds the system as the high-contrast text and structural chrome color.
- **Surface Canvas**: Surfaces prioritize pristine separation through `#FFFFFF` (base cards and sheets), `#F8FAFC` (canvas background), and `#F1F5F9` (recessed track inputs and tonal fills).
- **Line Network Wayfinding**: Mumbai Metro transit lines maintain strict, unadulterated color identities:
  - **Line 1 (Blue Line)**: `#0284C7` (Versova–Andheri–Ghatkopar)
  - **Line 2A/2B (Yellow Line)**: `#EAB308` (Dahisar–DN Nagar–Mandale)
  - **Line 3 (Aqua Line)**: `#06B6D4` (Colaba–Bandra–SEEPZ)
  - **Line 7 (Red Line)**: `#EF4444` (Dahisar East–Andheri East)
- **Operational Status & Indicators**:
  - **Normal Operation**: `#10B981` (On time / Active)
  - **Minor Delays**: `#F59E0B` (Caution / Maintenance)
  - **Severe Disruption / Alert**: `#EF4444` (Suspended / Closed)
  - **Interchange Nodes**: Dual-ring badges in `#8B5CF6` and `#F59E0B` to denote multi-line connectivity points.

## Typography

The type scale combines `Plus Jakarta Sans` for headers with `Inter` for data density and body text. 

`Plus Jakarta Sans` brings friendly geometric authority to station names, trip fares, and navigational headlines, softening the industrial weight of public infrastructure. `Inter` handles live schedules, platform numbers, interchange instructions, and distance metrics with tabular-grade neutrality and unmatched legibility at tiny viewports. 

For real-time timetable displays and platform numbers, numbers must be rendered with tabular figures (`tnum`) to eliminate layout shift during live arrivals countdowns.

## Layout & Spacing

This layout adheres strictly to an 8pt spatial grid, scaled down to 4pt only for micro-alignments like badge insets and transit line diagram nodes.

- **Mobile Viewports (Default)**: Uses a 4-column fluid layout with a `16px` outer margin (`margin: 1rem`) and `16px` gutters. Critical touch targets always occupy at least 48px in height to ensure error-free single-handed thumb operation on moving trains.
- **Card and Sheet Padding**: Core information cards (fare breakdowns, upcoming train times) use `space-md` (16px) internal padding, while quick-glance departure widgets use `space-lg` (24px) vertical rhythms to maintain spatial hierarchy.
- **Timeline & Node Spacing**: Vertical station itineraries utilize an unbroken 24px left track channel with stations spaced at consistent 32px increments to comfortably house delay chips, interchange tags, and platform notes.

## Elevation & Depth

Depth is established through a hybrid of Material You tonal layering and soft, diffused ambient lighting rather than heavy drop shadows:

- **Level 0 (Flat)**: Background canvas (`#F8FAFC`). Flat grouped rows, divider-less lists, and track routes.
- **Level 1 (Surface Cards & Sheets)**: Pure `#FFFFFF` background bound by a hairline outline (`1px solid #F1F5F9`) and a soft, low-contrast shadow: `0 1px 3px rgba(15, 23, 42, 0.04), 0 1px 2px rgba(15, 23, 42, 0.02)`.
- **Level 2 (Interactive Floating Modules & Route Pickers)**: Elevated cards with subtle upward projection: `0 4px 6px -1px rgba(15, 23, 42, 0.06), 0 2px 4px -2px rgba(15, 23, 42, 0.04)`.
- **Level 3 (Bottom Navigation & Draggable Route Drawers)**: High-prominence surface with an ambient diffuse shadow: `0 10px 15px -3px rgba(15, 23, 42, 0.08), 0 4px 6px -4px rgba(15, 23, 42, 0.03)` with a subtle top border `1px solid #E2E8F0`.

## Shapes

The geometric signature uses Level 2 roundedness (`0.5rem` / `8px` base corner radius) with selective full-radius pill styling for dynamic controls:

- **Cards, Modals, and Drawers**: Base cards maintain `0.5rem` (8px) corners. Large pull-up bottom sheets and modal dialogues scale to `1rem` (16px) on top edges to embrace the Material 3 mobile idiom.
- **Interactive Pills**: Action chips, filter tags, line indicator badges, and bottom-bar indicator capsules use full pill radii (`9999px`), ensuring sharp visual distinction between structural containers (squared-rounded) and interactive tokens (pill-shaped).
- **Transit Nodes**: Station transfer points utilize concentric circle geometries: 12px outer radius with a 6px hollow center for intermediate stops, and a 16px double-ring for interchange hubs.

## Components

- **Buttons**:
  - *Primary Action*: High-contrast `#1D4ED8` background, `#FFFFFF` text in `label-lg`, 48px height, 8px radius. Active ripple maps to `#1E3A8A`.
  - *Secondary / Tonal*: Soft `#EFF6FF` background with `#1D4ED8` text, ideal for "Reverse Route" or "View Timetable".
  - *Quick Navigation FAB*: Floating 56px action button with Level 2 elevation, positioned bottom-right above the navigation bar for instant ticket/QR scan access.

- **Chips & Filter Pills**:
  - *Line Filter Pills*: Pill radius (`9999px`), 32px height, soft slate border (`#E2E8F0`), housing a 8px colored dot corresponding to the Metro line (e.g., `#06B6D4` for Aqua Line 3).
  - *Status Badges*: Filled pill chips using light tint backgrounds (e.g., `#ECFDF5` for Normal Service) with high-contrast text (`#047857`) in `label-sm`.

- **Station Lists & Timeline Nodes**:
  - Continuous vertical rail line (3px stroke) in the line’s official color.
  - Standard stations: Solid white circle with 2.5px colored border.
  - Interchange stations: Expanded badge showing connected line color dots, transfer duration text in `body-sm`, and an amber/violet accent ring.

- **Input Fields & Route Selectors**:
  - Origin and Destination inputs live inside a unified Level 1 surface card. 
  - Origin indicated by a hollow blue node; Destination indicated by a solid red pin. 
  - Background uses `#F1F5F9` recessed fill with zero outline until focused, transitioning to a 2px `#1D4ED8` border with `#FFFFFF` background.

- **Cards**:
  - *Route Result Card*: White `#FFFFFF` card, 8px corner radius, hairline `#F1F5F9` border. Displays journey duration in `headline-md` (`Plus Jakarta Sans`), fare in Indian Rupees (`₹`), platform numbers, and transfer counts separated by subtle vertical dividers.

- **Bottom Navigation**:
  - Conforms to Material Design 3 guidelines: 80px container height, background `#FFFFFF` with Level 3 elevation.
  - Selected tab indicated by an active pill capsule (`#DBEAFE`) housing the icon, paired with `#0F172A` text in `label-md`. Inactive items stay neutral `#64748B`.